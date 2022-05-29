package uk.ac.leedsbeckett.student;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.CourseModelAssembler;
import uk.ac.leedsbeckett.student.model.CourseRepository;
import uk.ac.leedsbeckett.student.service.CourseService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CourseServiceTest {
    private Course course;
    String courseTitle = "ANA";
    String courseDescription = "Anatomy";
    Long courseId = 1L;
    Double courseFee = 20.0;
    @MockBean
    private CourseRepository courseRepository;
    @SpyBean
    private CourseModelAssembler courseModelAssembler;
    @Autowired
    private CourseService courseService;

    @BeforeEach
    public void setup(){
        course = new Course();
        course.setId(courseId);
        course.setTitle(courseTitle);
        course.setDescription(courseDescription);
        course.setFee(courseFee);

        Mockito.when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(course));
        Mockito.when(courseRepository.save(course))
                .thenReturn(course);
        Mockito.doNothing().when(courseRepository).delete(course);
    }

    @Test
    void testGetCourseById_withValidId_ReturnsExistingCourse(){
        EntityModel<Course> result = courseService.getCourseByIdJson(courseId);
        assertThat(courseId.equals(result.getContent().getId()));
        verify(courseModelAssembler, times(1)).toModel(course);
    }

    @Test
    void testGetCourseById_withInvalidID_throwsException(){
        assertThrows(RuntimeException.class,() -> courseService.getCourseByIdJson(1000L),
                "Exception was not thrown");
        verify(courseModelAssembler, times(0)).toModel(any());
    }

    @Test
    void testGetCourseById_withZeroID_throwsException(){
        assertThrows(RuntimeException.class,() -> courseService.getCourseByIdJson(0L),
                "Exception was not thrown");
        verify(courseModelAssembler, times(0)).toModel(any());
    }

    @Test
    void testGetCourseById_withNullID_throwsException(){
        assertThrows(RuntimeException.class,() -> courseService.getCourseByIdJson(null),
                "Exception was not thrown");
        verify(courseModelAssembler, times(0)).toModel(any());
    }


}
