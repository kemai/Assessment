package uk.ac.leedsbeckett.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.CourseRepository;
import uk.ac.leedsbeckett.student.service.CourseService;
import java.util.List;

@Controller
public class CourseController {

   private CourseService courseService;

   public CourseController(CourseService courseService){
       this.courseService = courseService;
   }

    @GetMapping("/courses")
    public ModelAndView getCourses(){
        List<Course> courseList = courseService.getAllCourses();
        ModelAndView modelAndView = new ModelAndView("courses");
        modelAndView.addObject("courses",courseList);
        return modelAndView;
    }
}
