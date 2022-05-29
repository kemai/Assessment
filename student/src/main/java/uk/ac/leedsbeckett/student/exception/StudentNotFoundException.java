package uk.ac.leedsbeckett.student.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(Long id) {
        super("Could not find Student with student id " + id);
    }

}