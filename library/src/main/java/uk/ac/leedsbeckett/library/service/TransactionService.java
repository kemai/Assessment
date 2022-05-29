package uk.ac.leedsbeckett.library.service;

import org.springframework.stereotype.Component;


import uk.ac.leedsbeckett.library.model.TransactionRepository;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    
}
