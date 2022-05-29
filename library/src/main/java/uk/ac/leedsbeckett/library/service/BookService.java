package uk.ac.leedsbeckett.library.service;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.leedsbeckett.library.controller.BookController;
import uk.ac.leedsbeckett.library.exception.AccountNotFoundException;
import uk.ac.leedsbeckett.library.model.Book;
import uk.ac.leedsbeckett.library.model.BookModelAssembler;
import uk.ac.leedsbeckett.library.model.BookRepository;

import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookService {

    private final BookRepository bookRepository;
    private final BookModelAssembler bookModelAssembler;

    public BookService(BookRepository bookRepository, BookModelAssembler bookModelAssembler){
        this.bookRepository = bookRepository;
        this.bookModelAssembler = bookModelAssembler;
    }

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }


    public CollectionModel<EntityModel<Book>> getAllBooksJson(){
        List<EntityModel<Book>> coursList = bookRepository.findAll()
                .stream()
                .map(bookModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(coursList, linkTo(methodOn(BookController.class)
                .getBooksJson())
                .withSelfRel());
    }

    public EntityModel<Book> getBookByIdJson(Long isbn){
        Book book = bookRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Book with isbn" + isbn + " not found"));
        return bookModelAssembler.toModel(book);


    }

    public ResponseEntity<EntityModel<Book>> createNewBookJson(Book newBook){
        Book savedBook = bookRepository.save(newBook);

        EntityModel<Book> entityModel = bookModelAssembler.toModel(savedBook);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<EntityModel<Book>> updateBookJson(Long isbn, Book newBook){
        Book existingBook = bookRepository.findById(isbn).orElseThrow(RuntimeException::new);
        existingBook.setN_copies(newBook.getN_copies());
        bookRepository.save(existingBook);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bookModelAssembler.toModel(existingBook));
    }

    public EntityModel<Book> getBookByTitle(String title) {
        Book book = bookRepository.findBookByTitle(title);
        if (book == null) {
            throw new AccountNotFoundException(title);
        }
        return bookModelAssembler.toModel(book);
    }



    public String findBookThroughPortal(Book book, BindingResult bindingResult, Model model) {
        if (book == null || book.getIsbn() == null) {
            throw new RuntimeException("Error");
        }
        if (bindingResult.hasErrors()) {
            return "portal";
        }
        Book found = getBookByTitle(book.getTitle()).getContent();
        model.addAttribute("book", found);
        return "book";
    }



}
