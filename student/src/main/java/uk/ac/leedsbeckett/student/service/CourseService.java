package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.CourseRepository;
import java.util.List;

@Component
public class CourseService {

    private CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository){
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }
}

