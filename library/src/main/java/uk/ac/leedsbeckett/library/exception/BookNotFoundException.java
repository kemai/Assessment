package uk.ac.leedsbeckett.library.exception;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Could not find book " + id);
    }

    public BookNotFoundException(String title) {
        super("Could not find book by Title " + title);
    }

    public BookNotFoundException() {
        super("Could not find book.");
    }

}
