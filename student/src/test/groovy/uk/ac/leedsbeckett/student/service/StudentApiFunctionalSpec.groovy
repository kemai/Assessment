package uk.ac.leedsbeckett.student.service

import geb.spock.GebSpec
import groovyx.net.http.RESTClient
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.jdbc.Sql


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles('test')
class StudentApiFunctionalSpec extends GebSpec {

    def path = 'http://localhost:8094/api/'
    def client

    def setup(){
        client = new RESTClient(path, MediaType.APPLICATION_JSON)
    }

    @Sql(['/clear-db.sql','/insert-student.sql'])
    def 'Testing GET a student by ID returns the correct student'(){

        when : 'a GET request is sent to get a student by id'
        def response = client.get(path: 'students/1')

        then: 'the correct response is returned'
        with(response){
            status == 200
            data.id == 1
            data.firstname == 'FRED'
            data.surname == 'MALL'
            data.externalStudentId == 'C735654'
            data._links.containsKey 'self'
        }


    }
}
