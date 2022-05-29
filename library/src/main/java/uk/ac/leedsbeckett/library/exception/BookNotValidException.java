package uk.ac.leedsbeckett.library.exception;

public class BookNotValidException extends RuntimeException {

    public BookNotValidException() {
        super("Not a valid Book.");
    }
    public BookNotValidException(String message) {
        super(message);
    }
}