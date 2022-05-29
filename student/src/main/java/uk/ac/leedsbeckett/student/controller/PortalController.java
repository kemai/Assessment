package uk.ac.leedsbeckett.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.service.CourseService;
import uk.ac.leedsbeckett.student.service.StudentService;


@Controller
public class PortalController {

    private final StudentService studentService;
    private final CourseService courseService;

    public PortalController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping({ "/"})
    public String redirectToHome(Model model) {
        return "redirect:/index";
    }

    @GetMapping({ "/index", "/index/student"})
    public String showPortal(Model model) {
        return studentService.showPortal(model);
    }

    @PostMapping("/index/course")
    public String findCourse(@ModelAttribute Course course, BindingResult bindingResult, Model model) {
        return courseService.findCourseThroughPortal(course, bindingResult, model);
    }


}