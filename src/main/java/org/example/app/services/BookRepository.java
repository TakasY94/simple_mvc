package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private Logger logger = Logger.getLogger(BookRepository.class);
    //private final List<Book> repo = new ArrayList<>();
    private final List<Book> repoFiltered = new ArrayList<>();
    private ApplicationContext context;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retreiveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        //if (filtered) return new ArrayList<>(repoFiltered);
        //else return new ArrayList<>(repo);
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books(author, title, size) VALUES(:author, :title, :size)", parameterSource);
        logger.info("store new book" + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", bookIdToRemove);
        jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed7");
        return true;
    }

    @Override
    public boolean removeItemByRegularExpression(BookToRemove bookToRemove) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", "%");
        parameterSource.addValue("title", "%");
        parameterSource.addValue("size", "%");
        if (!bookToRemove.getAuthor().isEmpty()) parameterSource.addValue("author", bookToRemove.getAuthor());
        if (!bookToRemove.getTitle().isEmpty()) parameterSource.addValue("title", bookToRemove.getTitle());
        if (bookToRemove.getSize() != null) parameterSource.addValue("size", bookToRemove.getSize());
        jdbcTemplate.update("SELECT * FROM books WHERE author LIKE :author AND title LIKE :title AND size LIKE :size", parameterSource );
        return true;
    }

    @Override
    public List<Book> filterItemByRegularExpression(BookToFilter bookToFilter) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", "%");
        parameterSource.addValue("title", "%");
        parameterSource.addValue("size", "%");
        if (!bookToFilter.getAuthor().isEmpty()) parameterSource.addValue("author", bookToFilter.getAuthor());
        if (!bookToFilter.getTitle().isEmpty()) parameterSource.addValue("title", bookToFilter.getTitle());
        if (bookToFilter.getSize() != null) parameterSource.addValue("size", bookToFilter.getSize());
        List<Book> books = jdbcTemplate.query("SELECT * FROM books WHERE author LIKE :author AND title LIKE :title AND size LIKE :size", parameterSource, (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });

        return new ArrayList<>(books);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    private void defaultInit(){
        logger.info("default INIT in book repo");
    }


    private void defaultDestroy(){
        logger.info("default DESTROY in book repo");

    }
}
