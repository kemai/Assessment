package uk.ac.leedsbeckett.student.service

import geb.spock.GebSpec
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles('test')
class StudentApplicationFunctionalSpec extends GebSpec {

    def setupSpec(){
        System.setProperty('webdriver.gecko.driver', 'C:\\Program Files\\WebDrivers\\geckodriver.exe')
    }

    @Sql(['/clear-db.sql','/insert-student.sql'])
    def 'Getting student profile'(){

        when: 'I go to the Student profile page'
        browser.drive{
            go 'http://localhost:8094/students/1'
        }

        then: 'I land on the right page'
        title == 'Student Profile'

        and: 'It has the correct heading'
        $('h2').text() == 'Student Profile'

        and: 'The student details are displayed correctly'
        $('p').size() == 1
        $('p').first().text() == 'First Name: FRED'
        $('p')[1].text() == 'Surname: MALL'
        $('p').last().text() == 'Student ID: C735654'

    }
}
