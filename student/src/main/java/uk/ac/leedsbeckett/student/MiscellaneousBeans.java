package uk.ac.leedsbeckett.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import uk.ac.leedsbeckett.student.model.*;
import uk.ac.leedsbeckett.student.service.EnrolmentService;
import uk.ac.leedsbeckett.student.service.IntegrationService;


import java.util.Locale;
import java.util.Set;




@Configuration
public class MiscellaneousBeans {
    /*
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }


     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.UK);
        return sessionLocaleResolver;
    }


    @Bean
    CommandLineRunner initDatabase(CourseRepository courseRepository, StudentRepository studentRepository, IntegrationService integrationService, EnrolmentService enrolmentService){
        return args -> {

            Account account;

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

            Course hst = new Course();
            hst.setTitle("HST");
            hst.setDescription("History");
            hst.setFee(35.00);

            Course ai = new Course();
            ai.setTitle("AI");
            ai.setDescription("Artificial Intelligence");
            ai.setFee(55.00);

            Course am = new Course();
            am.setTitle("AM");
            am.setDescription("Agile Methodology");
            am.setFee(20.00);

         //  courseRepository.saveAllAndFlush(Set.of(sesc,ntwk,pm,hst,ai,am));
            
            // Student creation


            Student abba = new Student();
            abba.setSurname("Abba");
            abba.setFirstname("Gale");
            abba.setExternalStudentId("c9999");
            /*
            abba.enrolInCourse(ntwk);
            abba.enrolInCourse(ai);
            abba.enrolInCourse(hst);


             */

            Student marco = new Student();
            marco.setSurname("Brescia");
            marco.setFirstname("Marco");
            marco.setExternalStudentId("c2222");
            /*
            marco.enrolInCourse(sesc);
            marco.enrolInCourse(am);
            marco.enrolInCourse(ai);


             */
            Student pedro = new Student();
            pedro.setSurname("Alvarez");
            pedro.setFirstname("Pedro");
            pedro.setExternalStudentId("c3333");
            /*
            pedro.enrolInCourse(pm);
            pedro.enrolInCourse(hst);
            pedro.enrolInCourse(sesc);

             */

            studentRepository.saveAllAndFlush(Set.of(abba,marco,pedro));

            // test data for new account in the finance service
          //  enrolmentService.createNewAccount(marco);
          //  enrolmentService.createNewAccount(pedro);
          //  enrolmentService.createNewAccount(abba);

            //test data for new account in library service

            enrolmentService.createNewLibrayAccount(marco);
            enrolmentService.createNewLibrayAccount(pedro);
            enrolmentService.createNewLibrayAccount(abba);


            enrolmentService.enrolInCourseIntegration(marco,sesc);
            enrolmentService.enrolInCourseIntegration(marco,ai);
            enrolmentService.enrolInCourseIntegration(marco,ntwk);
            enrolmentService.enrolInCourseIntegration(marco,am);

            enrolmentService.enrolInCourseIntegration(pedro,pm);
            enrolmentService.enrolInCourseIntegration(pedro,hst);

            enrolmentService.enrolInCourseIntegration(abba,ai);
            enrolmentService.enrolInCourseIntegration(abba,am);


        };

    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
