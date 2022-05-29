package uk.ac.leedsbeckett.student.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification
import uk.ac.leedsbeckett.student.model.Course

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class CourseServiceIntegrationTest extends Specification {

    @Autowired
    private CourseService courseService

    @Sql(['/clear-db.sql','/insert-courses.sql'])
    def 'Testing getCourseJson() reads a course from the database'(){

        when: 'we read the Course from the database'
        def result = courseService.getCourseByIdJson(1L)

        then: 'all attributes are fetched correctly'
        result.content.id == 1L
        result.content.description == 'CLOUD COMPUTING'
        result.content.fee == 30.0
        result.content.title == 'CC'

    }

    @Sql(['/clear-db.sql','/insert-courses.sql'])
    def 'Testing getAllCourseJson() reads a course from the database'(){

        when: 'we read the Course from the database'
        def result = courseService.getAllCoursesJson()

        then: ' all three course are fetched'
        result.getContent().size() == 3

        and : 'their attributes are as expected'
        result.content
        .collect(EntityModel::getContent)
        .collect(Course::getTitle) == ['CC', 'RP', 'PHY']
    }

    @Sql(['/clear-db.sql','/insert-courses.sql'])
    def 'Testing createNewCourseJson() insert a new course in the database'(){

        given: 'a new course'
        def course = new Course()
        course.title = 'EHS'
        course.description = 'European History'
        course.fee = 30.50

        when: 'we insert the course in the database'
        def result = courseService.createNewCourseJson(course)

        then: 'the course is persisted'
        def returnedCourse = result.body.content
        returnedCourse.id > 0

        and: 'its attributes are as expected'
        returnedCourse.title == 'EHS'
        returnedCourse.description == 'European History'
        returnedCourse.fee == 30.50
    }

    @Sql(['/clear-db.sql','/insert-courses.sql'])
    def 'Testing updateCourseJson() modify a course in the database'(){

        given: 'an existing course from the database'
        def course = courseService.getCourseByIdJson(1).content

        when: 'we modify the course'
        course.description = 'Dummy Course'

        and: 'save the changes to the database'
        def result = courseService.updateCourseJson(1,course)

        then: ' the change are persisted'
        result.body.content.id == 1
        result.body.content.description == 'Dummy Course'
    }


}
