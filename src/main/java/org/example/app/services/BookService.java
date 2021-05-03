package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookRepo;

    @Autowired
    public BookService(ProjectRepository<Book> bookRepo) {
        this.bookRepo = bookRepo;
    }


    public List<Book> getAllBooks(Boolean filtered){
        return bookRepo.retreiveAll(filtered);
    }

    public void saveBook(Book book){
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }
    public boolean removeItemByRegularExpression(String author, String title, String size) {
        return bookRepo.removeItemByRegularExpression(author, title, size );
    }

    public boolean filterItemByRegularExpression(String author, String title, String size) {
        return bookRepo.filterItemByRegularExpression(author, title, size );
    }

}
