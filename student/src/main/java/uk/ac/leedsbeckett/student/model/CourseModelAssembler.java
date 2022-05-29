package uk.ac.leedsbeckett.student.model;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import uk.ac.leedsbeckett.student.controller.CourseController;
import uk.ac.leedsbeckett.student.exception.CourseNotValidException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CourseModelAssembler implements RepresentationModelAssembler<Course,EntityModel<Course>>{

    @Override
    public EntityModel<Course> toModel(Course course) {
        if (course.getId()== null || course.getId()== 0){
            throw new CourseNotValidException();
        }
        return EntityModel.of(course,
               linkTo(methodOn(CourseController.class).getCourseJson(course.getId())).withSelfRel(),
               linkTo(methodOn(CourseController.class).getCoursesJson()).withRel("courses"));
    }
}
