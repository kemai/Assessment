package uk.ac.leedsbeckett.student.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import uk.ac.leedsbeckett.student.model.Account;
import uk.ac.leedsbeckett.student.service.IntegrationService;


public class IntegrationController {

    public final IntegrationService integrationService;

    public IntegrationController(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }

    @GetMapping("/api/finance/students/{id}")
    public Account getFinanceAccount(@PathVariable String studentId){
        return integrationService.getStudentAccount(studentId);
    }



  //  @PostMapping("")

}
