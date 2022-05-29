package uk.ac.leedsbeckett.library.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.library.model.Book;
import uk.ac.leedsbeckett.library.service.BookService;
import uk.ac.leedsbeckett.library.service.IntegrationService;

import java.util.List;

@Controller
public class BookController {

    private BookService bookService;
    private IntegrationService integrationService;

    public BookController(BookService bookService){
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ModelAndView getallBooks(Long id){
        List<Book> bookList = bookService.getAllBooks();
        ModelAndView modelAndView = new ModelAndView("books");
        modelAndView.addObject("books",bookList);
        return modelAndView;
    }

    @GetMapping("api/books")
    @ResponseBody
    public CollectionModel<EntityModel<Book>> getBooksJson(){
        return bookService.getAllBooksJson();
    }

    @GetMapping("api/books/{id}")
    @ResponseBody
    public EntityModel<Book> getBookJson(@PathVariable String isbn){
        return bookService.getBookByIdJson(isbn);
    }

    @PostMapping("api/books")
    ResponseEntity<EntityModel<Book>> createBookJson(@RequestBody Book newBook){
        return bookService.createNewBookJson(newBook);
    }

    @PutMapping("/api/books/{id}")
    ResponseEntity<?> editBookJson(@PathVariable Long id, @RequestBody Book newBook){
        return bookService.updateBookJson(id,newBook);
    }

    // Get the
    @PostMapping("/books")
    Book createBookISBN(@RequestBody Book newBook){
        return integrationService.getBookByISBN(newBook.getIsbn());
    }

}
