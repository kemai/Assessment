package uk.ac.leedsbeckett.student.controller;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.service.CourseService;
import java.util.List;

@Controller
public class CourseController {

   private CourseService courseService;

   public CourseController(CourseService courseService){
       this.courseService = courseService;
   }

    @GetMapping("/courses")
    public ModelAndView getCourses(Long id){
        List<Course> courseList = courseService.getAllCourses();
        ModelAndView modelAndView = new ModelAndView("courses");
        modelAndView.addObject("courses",courseList);
        return modelAndView;
    }

    @GetMapping("api/courses")
    @ResponseBody
    public CollectionModel<EntityModel<Course>> getCoursesJson(){
        return courseService.getAllCoursesJson();
    }

    @GetMapping("api/courses/{id}")
    @ResponseBody
    public EntityModel<Course> getCourseJson(@PathVariable Long id){
       return courseService.getCourseByIdJson(id);
    }

    @PostMapping("api/courses")
    ResponseEntity<EntityModel<Course>> createCourseJson(@RequestBody Course newCourse){
        return courseService.createNewCourseJson(newCourse);
    }

    @PutMapping("/api/courses/{id}")
    ResponseEntity<?> editCourseJson(@PathVariable Long id, @RequestBody Course newCourse){
        return courseService.updateCourseJson(id,newCourse);
    }

}
