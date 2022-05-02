package uk.ac.leedsbeckett.student.controller;


import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.service.StudentService;

@Controller
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/api/students/{id}")
    @ResponseBody
    public EntityModel<Student> getStudentJson(@PathVariable long id){
        return studentService.getStudentByIdJson(id);
    }
}
