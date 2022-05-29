package uk.ac.leedsbeckett.student;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.CourseModelAssembler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CourseAssemblerTest {

    private Course course;

    @Autowired
    private CourseModelAssembler courseModelAssembler;

    @BeforeEach
    public void setUp(){

    }

    @Test
    void testToModel_withValidID_ReturnsExpectedEntityModel(){
        EntityModel<Course> result = courseModelAssembler.toModel(course);
        assertThat(course.equals((result.getContent())));
        assertThat(result.getLinks().hasSize(2));
        assertThat(result.hasLink("http://localhost/courses/1"));
        assertThat(result.hasLink("http://localhoust/courses"));
    }

    @Test
    void testToModel_withIDNull_ThrowsException(){
        course.setId(null);
        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(course),
                "Exception was not thrown.");

    }

    @Test
    void testToModel_withIDZero_ThrowsException(){
        course.setId(0L);
        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(course),
                "Exception was not thrown.");

    }

    @Test
    void testToModel_withNullArgument_ThrowsException(){

        assertThrows(RuntimeException.class, () -> courseModelAssembler.toModel(course),
                "Exception was not thrown.");

    }


}
