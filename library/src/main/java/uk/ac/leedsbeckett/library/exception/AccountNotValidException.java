package uk.ac.leedsbeckett.library.exception;

public class AccountNotValidException extends RuntimeException {

    public AccountNotValidException() {
        super("Not a valid account.");
    }
    public AccountNotValidException(String message) {
        super(message);
    }
}