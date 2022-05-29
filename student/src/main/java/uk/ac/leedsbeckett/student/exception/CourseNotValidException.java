package uk.ac.leedsbeckett.student.exception;

public class CourseNotValidException extends RuntimeException {

    public CourseNotValidException() {
        super("Not a valid course.");
    }
    public CourseNotValidException(String message) {
        super(message);
    }
}