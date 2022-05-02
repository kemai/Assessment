package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;
import org.springframework.hateoas.EntityModel;


@Component
public class StudentService {

    private final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public EntityModel<Student> getStudentByIdJson(long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course with id" +id + "not found"));
                return EntityModel.of(student);
    }
}
