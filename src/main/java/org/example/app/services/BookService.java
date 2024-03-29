package org.example.app.services;

import org.example.web.dto.Book;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import java.util.List;


@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;
    private final Logger logger = Logger.getLogger(BookService.class);

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<Book> getAllBooks(){
        return bookRepo.retreiveAll();
    }

    public void saveBook(Book book){
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }
    public boolean removeItemByRegularExpression(BookToRemove bookToRemove) {
        return bookRepo.removeItemByRegularExpression(bookToRemove);
    }

    public List<Book> filterItemByRegularExpression(BookToFilter bookToFilter) {
        return bookRepo.filterItemByRegularExpression(bookToFilter);
    }

    private void defaultInit(){
        logger.info("default INIT in book service");
    }


    private void defaultDestroy(){
        logger.info("default DESTROY in book service");

    }

}
