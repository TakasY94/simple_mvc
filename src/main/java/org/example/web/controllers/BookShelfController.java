package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model, @RequestParam(value = "filtered" , defaultValue = "false") Boolean filtered){
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks(filtered));
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book){
        if ( (book.getAuthor() != null && book.getAuthor() != "" )||(book.getTitle() != null && book.getTitle() != "" ) || (book.getSize() != null && Integer.parseInt(book.getSize()) > 0 ) ){
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks(false).size());
            return "redirect:/books/shelf";
        }else {
            logger.info("attempt saving empty book");
            return "redirect:/books/shelf";
        }


    }

    //User can delete book by any parameter
    @PostMapping("/remove")
    public String removeBook(Model model, @RequestParam(value = "bookIdToRemove" , required=false) Integer bookIdToRemove, @RequestParam(value = "author" , required=false) String author, @RequestParam(value = "title" , required=false) String title, @RequestParam(value = "size" , required=false) String size){
        boolean someBookWasFound = false;
        if (bookIdToRemove != null && bookIdToRemove != 0) {
            someBookWasFound = bookService.removeBookById(bookIdToRemove);
        }
        else someBookWasFound = bookService.removeItemByRegularExpression(author, title, size );

        if (someBookWasFound){
            return "redirect:/books/shelf";
        }else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks(false));
            return "book_shelf";
        }
    }

    @PostMapping("/filter")
    public String filterBook(Model model, @RequestParam(value = "author" , required=false) String author, @RequestParam(value = "title" , required=false) String title, @RequestParam(value = "size" , required=false) String size){
        boolean someBookWasFound = false;
        someBookWasFound = bookService.filterItemByRegularExpression(author, title, size );

        if (someBookWasFound){
            return "redirect:/books/shelf?filtered=true";
        }else {
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks(false));
            return "book_shelf";
        }
    }

}
