package uk.ac.leedsbeckett.student.controller;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.service.StudentService;

@Controller
public class StudentController {
    public final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/api/students/{id}")
    @ResponseBody
    public EntityModel<Student> getStudentJson(@PathVariable long id){
        return studentService.getStudentByIdJson(id);
    }

    @GetMapping("/students/{id}")
    public ModelAndView getStudent(@PathVariable Long id){
        return studentService.getStudentbyId(id);
    }

    @GetMapping("api/students")
    @ResponseBody
    public CollectionModel<EntityModel<Student>> getStudentsJson(){
        return studentService.getAllStudentsJson();
    }

    @PostMapping("api/students")
    ResponseEntity<EntityModel<Student>> createStudentJson(@RequestBody Student newStudent){
        return studentService.createNewStudentJson(newStudent);
    }

    @PutMapping("/api/students/{id}")
    ResponseEntity<?> editStudentJson(@PathVariable Long id, @RequestBody Student newStudent){
        return studentService.updateStudentJson(id,newStudent);
    }
    @DeleteMapping("/students/{id}")
    ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        return studentService.deleteStudent(id);
    }


}

