package uk.ac.leedsbeckett.student.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql
import spock.lang.Specification
import uk.ac.leedsbeckett.student.model.Course
import uk.ac.leedsbeckett.student.model.Student

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
class StudentServiceIntegrationTest extends Specification {

    @Autowired
    private StudentService studentService
    private Student student = new Student();

    @Sql(['/clear-db.sql', '/insert-student.sql'])
    def 'Testing GetStudentJson() reads a student from database'() {
        when: 'we read the student from the database'
        def result = studentService.getStudentByIdJson(1L)

        then: 'all the attribute are fetched correctly'
        result.content.id == 1L
        result.content.firstname == 'FRED'
        result.content.surname == 'MALL'
        result.content.externalStudentId == 'C735654'


    }


    @Sql(['/clear-db.sql', '/insert-student.sql'])
    def 'Testing createNewStudentJson() insert a new student in the database'()
    {
        when: 'we create a new Student'
        student.setSurname("roger")
        student.setFirstname("finn")
        student.setExternalStudentId("c8888")
        studentService.createNewStudentJson(student)
        def result = studentService.getStudentByIdJson(3L)

        then: 'all the attribute are fetched correctly'
        result.content.id == 3L
        result.content.firstname == 'finn'
        result.content.surname == 'roger'
        result.content.externalStudentId == 'c8888'

    }

    @Sql(['/clear-db.sql', '/insert-student.sql'])
    def 'Testing updateStudentJson() modify a student in the database'()
    {
        when: 'we update the student information'
        student.setFirstname("roy")
        student.setSurname("jen")

        studentService.updateStudentJson(1L,student)

        def result = studentService.getStudentByIdJson(1L)

        then: 'all the attribute are fetched correctly'
        result.content.id == 1L
        result.content.firstname == 'roy'
        result.content.surname == 'jen'
        result.content.externalStudentId == 'C735654'

    }

    @Sql(['/clear-db.sql', '/insert-student.sql'])
    def 'Testing deleteStudent() delete a student from the database'()
    {

        when: 'we delete a student'

        studentService.deleteStudent(1L)

        def result = studentService.getStudentByIdJson(1L)

        then: 'all the attribute are fetched correctly'
        result.content.id == null

    }


}
