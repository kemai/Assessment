package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;

@Component
public class EnrolmentService {
    private final StudentRepository studentRepository;

    public EnrolmentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public void enrolStudentInCourse(Student student, Course course){
        student.enrolInCourse(course);
        studentRepository.save(student);
    }
}
