package uk.ac.leedsbeckett.library.model;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.library.controller.BookController;
import uk.ac.leedsbeckett.library.exception.BookNotFoundException;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class BookModelAssembler implements RepresentationModelAssembler<Book,EntityModel<Book>>{

    @Override
    public EntityModel<Book> toModel(Book book) {
        if (book.getIsbn()== null || book.getIsbn().isEmpty()){
            throw new BookNotFoundException();
        }
        return EntityModel.of(book,
               linkTo(methodOn(BookController.class).getBookJson(Long.valueOf(book.getIsbn()))).withSelfRel(),
               linkTo(methodOn(BookController.class).getBooksJson()).withRel("books"));
    }
}
