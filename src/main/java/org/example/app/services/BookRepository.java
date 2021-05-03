package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>{

    private Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private final List<Book> repoFiltered = new ArrayList<>();

    @Override
    public List<Book> retreiveAll(Boolean filtered) {
        if (filtered) return new ArrayList<>(repoFiltered);
        else return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(book.hashCode());
        logger.info("store new book" + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        boolean success = false;
        for(Book book : retreiveAll(false)){
            if (book.getId().equals(bookIdToRemove)){
                logger.info("remove book completed: " + book);
                repo.remove(book);
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean removeItemByRegularExpression(String author, String title, String size) {
        boolean success = false;
        Pattern authorPattern = Pattern.compile(author, Pattern.CASE_INSENSITIVE);
        Pattern titlePattern = Pattern.compile(title, Pattern.CASE_INSENSITIVE);
        Pattern sizePattern = Pattern.compile(size, Pattern.CASE_INSENSITIVE);
        for(Book book : retreiveAll(false)){
            if (author != null && !author.isEmpty()  && authorPattern.matcher(book.getAuthor()).find()) {
                repo.remove(book);
                logger.info("remove book completed: " + book);
                success = true;
                continue;
            }
            if (title != null && !title.isEmpty() &&  titlePattern.matcher(book.getTitle()).find()) {
                repo.remove(book);
                logger.info("remove book completed: " + book);
                success = true;
                continue;
            }
            if (size != null && !size.isEmpty() &&  sizePattern.matcher(book.getSize()).find()){
                repo.remove(book);
                logger.info("remove book completed: " + book);
                success = true;
                continue;
            }

        }
        return success;
    }

    @Override
    public boolean filterItemByRegularExpression(String author, String title, String size) {
        boolean success = false;
        Pattern authorPattern = Pattern.compile(author, Pattern.CASE_INSENSITIVE);
        Pattern titlePattern = Pattern.compile(title, Pattern.CASE_INSENSITIVE);
        Pattern sizePattern = Pattern.compile(size, Pattern.CASE_INSENSITIVE);
        repoFiltered.clear();
        for(Book book : retreiveAll(false)){
            if (author != null && !author.isEmpty()  && authorPattern.matcher(book.getAuthor()).find()) {
                repoFiltered.add(book);
                logger.info("book filtered: " + book);
                success = true;
                continue;
            }
            if (title != null && !title.isEmpty() &&  titlePattern.matcher(book.getTitle()).find()) {
                repoFiltered.add(book);
                logger.info("book filtered: " + book);
                success = true;
                continue;
            }
            if (size != null && !size.isEmpty() &&  sizePattern.matcher(book.getSize()).find()){
                repoFiltered.add(book);
                logger.info("book filtered: " + book);
                success = true;
                continue;
            }

        }
        return success;
    }
}
