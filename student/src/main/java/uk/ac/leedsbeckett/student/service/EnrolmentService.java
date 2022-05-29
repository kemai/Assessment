package uk.ac.leedsbeckett.student.service;

import org.springframework.cache.annotation.AbstractCachingConfiguration;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.student.model.*;

import java.time.LocalDate;


@Component
public class EnrolmentService {
    private final IntegrationService integrationService;

    private final StudentRepository studentRepository;

    public EnrolmentService(StudentRepository studentRepository,IntegrationService integrationService) {
        this.studentRepository = studentRepository;
        this.integrationService = integrationService;
    }

    public Account getFinanceAccount(Student student){
       return integrationService.getStudentAccount(student.getExternalStudentId());
    }



    public void enrolInCourseIntegration(Student student, Course course) {
            if (studentRepository.findStudentByCoursesEnrolledInContains(course.getId())== null){
                student.enrolInCourse(course);
            }
            studentRepository.save(student);

            if(getFinanceAccount(student)== null){
               createNewAccount(student);
            }

            integrationService.createCourseFeeInvoice(createInvoice(student, course));
    }

    public void createNewAccount(Student student){
        Account account = new Account();
        account.setStudentId(student.getExternalStudentId());
        if(integrationService.getStudentAccount(account.getStudentId()) == null){
            integrationService.createStudentAccount(account);
        }

    }

    // create a new library account with the student ID
    public void createNewLibrayAccount(Student student){
        Account account = new Account();
        account.setStudentId(student.getExternalStudentId());
        if(integrationService.getLibraryAccount(account.getStudentId())== null){
            integrationService.createBookAccount(account);
        }
    }

    // create a new invoce
    private Invoice createInvoice(Student student,Course course){
        Account account = new Account();
        account.setStudentId(student.getExternalStudentId());
        Invoice invoice = new Invoice();
        invoice.setAccount(account);
        invoice.setAmount(course.getFee());
        invoice.setType(Invoice.Type.TUITION_FEES);
        invoice.setDueDate(LocalDate.now().plusMonths(3));
        return invoice;
    }


}
