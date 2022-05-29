package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.model.Account;
import uk.ac.leedsbeckett.student.model.Invoice;



@Component
public class IntegrationService {

    private final RestTemplate restTemplate;
    private String url_student = "http://localhost:8081/accounts/student/";
    private String url_invoice = "http://localhost:8081/invoices/";

    private String url_financeaccount = "http://localhost:8081/accounts/";

    private String url_bookaccount = "http://localhost:8085/accounts/";




    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Get the Account from the finance Service by studentID
    public Account getStudentAccount(String studentId){
        return restTemplate.getForObject(url_student + studentId, Account.class);
    }

    // Create a new Student Account into the finance service
    public Account createStudentAccount(Account account){
        return restTemplate.postForObject(url_financeaccount,account,Account.class);

    }

    // Create a new Invoice for the course enrol in
    public Invoice createCourseFeeInvoice(Invoice invoice){
        return restTemplate.postForObject(url_invoice, invoice, Invoice.class);
    }

    // create a new StudentAccount in the library
    public Account createBookAccount(Account account){
        return restTemplate.postForObject(url_bookaccount,account, Account.class);
    }

    public Account getLibraryAccount(String studentId){
        return restTemplate.getForObject(url_bookaccount + studentId, Account.class);
    }

}
