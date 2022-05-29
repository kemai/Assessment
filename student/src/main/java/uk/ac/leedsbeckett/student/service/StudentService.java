package uk.ac.leedsbeckett.student.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.controller.StudentController;
import uk.ac.leedsbeckett.student.exception.StudentNotFoundException;
import uk.ac.leedsbeckett.student.exception.StudentNotValidException;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentModelAssembler;
import uk.ac.leedsbeckett.student.model.StudentRepository;
import org.springframework.hateoas.EntityModel;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentModelAssembler studentModelAssembler;


    public StudentService(StudentRepository studentRepository, StudentModelAssembler studentModelAssembler) {
        this.studentRepository = studentRepository;
        this.studentModelAssembler = studentModelAssembler;
    }

    // Get a Student by id and return JSON response
    public EntityModel<Student> getStudentByIdJson(long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
                return studentModelAssembler.toModel(student);

    }

    // Get a Student by id
    public ModelAndView getStudentbyId(Long id){
        ModelAndView modelAndView = new ModelAndView("student");
        modelAndView.addObject(studentRepository.findById(id).orElseThrow(RuntimeException::new));
        return modelAndView;
    }

    // Get all Students by id and return JSON response
    public CollectionModel<EntityModel<Student>> getAllStudentsJson(){
        List<EntityModel<Student>> studentList = studentRepository.findAll()
                .stream()
                .map(studentModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(studentList,
                linkTo(methodOn(StudentController.class).getStudentsJson()).withSelfRel());
    }

    // Create a new Student and return JSON response

    public ResponseEntity<EntityModel<Student>> createNewStudentJson(Student newStudent){
        if(newStudent.getExternalStudentId() == null || newStudent.getExternalStudentId().isEmpty()){
            throw new StudentNotValidException();
        }
        Student savedStudent;
        try{
            savedStudent = studentRepository.save(newStudent);
        }catch (DataIntegrityViolationException e){
            throw new StudentNotValidException("A student with the same student ID  " + newStudent.getExternalStudentId());
        }

        EntityModel<Student> entityModel = studentModelAssembler.toModel(savedStudent);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    // Update Student information and return JSON response

    public ResponseEntity<EntityModel<Student>> updateStudentJson(Long id, Student newStudent){
       Student existingStudent = studentRepository.findById(id).orElseThrow(RuntimeException::new);
        if (newStudent.getFirstname() != null) {
            existingStudent.setFirstname(newStudent.getFirstname());
        }
        if(newStudent.getSurname()!= null){
            existingStudent.setSurname(newStudent.getSurname());
        }
        if(newStudent.getPassword()!= null){
            existingStudent.setExternalStudentId(newStudent.getPassword());
        }
        studentRepository.save(existingStudent);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(studentModelAssembler.toModel(existingStudent));
    }

    // Delete a Student
    public ResponseEntity<?> deleteStudent(Long id) {
        Student account = studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
        studentRepository.delete(account);
        return ResponseEntity.noContent().build();
    }

    // Method to show the Portal Home Page
    public String showPortal(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "portal";
    }


}
