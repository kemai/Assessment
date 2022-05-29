package uk.ac.leedsbeckett.student.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long id) {
        super("Could not find course " + id);
    }

    public CourseNotFoundException(String title) {
        super("Could not find invoice for title " + title);
    }

    public CourseNotFoundException() {
        super("Could not find course.");
    }

}
