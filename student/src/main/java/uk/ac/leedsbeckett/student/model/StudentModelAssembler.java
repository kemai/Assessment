package uk.ac.leedsbeckett.student.model;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.controller.StudentController;
import uk.ac.leedsbeckett.student.exception.StudentNotValidException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class StudentModelAssembler implements RepresentationModelAssembler<Student,EntityModel<Student>> {

    @Override
    public EntityModel<Student> toModel(Student student) {
        if (student.getId()== null || student.getId()== 0){
            throw new StudentNotValidException();
        }
        return (EntityModel<Student>) EntityModel.of(student,
                linkTo(methodOn(StudentController.class).getStudentJson(student.getId())).withSelfRel(),
                linkTo(methodOn(StudentController.class).getStudentsJson()).withRel("student"));
    }
}
