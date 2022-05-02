package uk.ac.leedsbeckett.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.model.Course;
import uk.ac.leedsbeckett.student.model.CourseRepository;
import uk.ac.leedsbeckett.student.model.Student;
import uk.ac.leedsbeckett.student.model.StudentRepository;

import java.util.Set;


@Configuration
public class MiscellaneousBeans {

    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, StudentRepository studentRepository){
        return args -> {
            //Course Creation
            Course sesc = new Course();
            sesc.setTitle("SESC");
            sesc.setDescription("Software Engineering for Service Computing");
            sesc.setFee(150.00);

            Course ntwk = new Course();
            ntwk.setTitle("NM");
            ntwk.setDescription("Network Management");
            ntwk.setFee(50.00);

            Course pm = new Course();
            pm.setTitle("PM");
            pm.setDescription("Project Management");
            pm.setFee(60.00);
            
            // Student creation

            Student abba = new Student();
            abba.setSurname("Abba");
            abba.setFirstName("Gale");
            abba.setExternalStudentId("c9999");
            abba.enrolInCourse(ntwk);

            Student marco = new Student();
            marco.setSurname("Brescia");
            marco.setFirstName("Marco");
            marco.setExternalStudentId("c2222");
            marco.enrolInCourse(sesc);

            Student pedro = new Student();
            pedro.setSurname("Alvarez");
            pedro.setFirstName("Pedro");
            pedro.setExternalStudentId("c3333");
            pedro.enrolInCourse(pm);

            studentRepository.saveAllAndFlush(Set.of(abba,marco,pedro));


        };

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
