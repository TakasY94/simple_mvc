package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.exceptions.BookShelfLoginException;
import org.example.app.exceptions.BookShelfUploadException;
import org.example.app.services.BookService;
import org.example.app.services.FileService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookToFilter;
import org.example.web.dto.BookToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Controller
@RequestMapping(value = "/books")
@Scope("singleton")
public class BookShelfController {
    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;
    private FileService fileService;

    @Autowired
    public BookShelfController(BookService bookService, FileService fileService) {
        this.bookService = bookService;
        this.fileService = fileService;
    }

    @GetMapping("/shelf")
    public String books(Model model, @RequestParam(value = "filtered" , defaultValue = "false") Boolean filtered){
        logger.info(this.toString());
        model.addAttribute("book", new Book());
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("bookToFilter", new BookToFilter());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        model.addAttribute("fileList", fileService.getListFilesForDownload(System.getProperty("catalina.home") + File.separator + "external_uploads" + File.separator));
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            model.addAttribute("book", book);
            model.addAttribute("bookToRemove", new BookToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }
    }

    //User can delete book by any parameter
    @PostMapping("/remove")
    public String removeBook(Model model, @Valid BookToRemove bookToRemove, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            model.addAttribute("bookToRemove", bookToRemove);
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else if (bookToRemove.getAuthor().isEmpty() && bookToRemove.getSize() != null && bookToRemove.getSize() == 0 && bookToRemove.getTitle().isEmpty()){
            bookService.removeBookById(bookToRemove.getId());
            return "redirect:/books/shelf";
        } else {
            bookService.removeItemByRegularExpression(bookToRemove);
            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/filter")
    public String filterBook(Model model, @Valid BookToFilter bookToFilter, BindingResult bindingResult){
        model.addAttribute("bookToRemove", new BookToRemove());
        model.addAttribute("bookToFilter", bookToFilter);
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        if (bindingResult.hasErrors()){
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        }else {
            model.addAttribute("bookList", bookService.filterItemByRegularExpression(bookToFilter));
        }
        return "book_shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file")MultipartFile file) throws Exception{
        if (file.isEmpty()) throw new BookShelfUploadException("File was not choosed");
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()){
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at :" + serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }

    @RequestMapping(value = "/files", method = RequestMethod.GET)
    public void downloadFile(@RequestParam(value="fileName") String fileName, HttpServletResponse response) throws IOException {
        // Прежде всего стоит проверить, если необходимо, авторизован ли пользователь и имеет достаточно прав на скачивание файла. Если нет, то выбрасываем здесь Exception

        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String appConfigPath = rootPath + "app.properties";
        Properties appProps = new Properties();
        appProps.load(new FileInputStream(appConfigPath));

        Path file = Paths.get(appProps.getProperty("file_dir_for_download"), fileName);
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);
            response.setContentType("application/vnd.ms-excel");

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                logger.info("Error writing file to output stream. Filename was '{}'" + fileName, e);
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

    @ExceptionHandler(BookShelfUploadException.class)
    public String handleError(Model model, BookShelfUploadException exception){
        model.addAttribute("errorMessage", exception.getMessage());
        return "errors/402";
    }
}
