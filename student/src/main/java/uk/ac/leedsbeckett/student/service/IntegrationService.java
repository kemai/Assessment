package uk.ac.leedsbeckett.student.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.ac.leedsbeckett.student.model.Account;
import uk.ac.leedsbeckett.student.model.Invoice;

import java.nio.channels.AcceptPendingException;

@Component
public class IntegrationService {

    private final RestTemplate restTemplate;
    private String url_database = "http://localhost:8081/accounts/student/";


    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Account getStudentAccount(String studentId){
        return restTemplate.getForObject(url_database + studentId, Account.class);
    }

    public Invoice createCourseFeeInvoice(Invoice invoice){
        return restTemplate.postForObject(url_database,invoice, Invoice.class);
    }

}
