package uk.ac.leedsbeckett.library.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import uk.ac.leedsbeckett.library.controller.AccountController;
import uk.ac.leedsbeckett.library.exception.AccountNotValidException;
import uk.ac.leedsbeckett.library.model.Account;
import uk.ac.leedsbeckett.library.model.Transaction;
import uk.ac.leedsbeckett.library.model.AccountModelAssembler;
import uk.ac.leedsbeckett.library.model.AccountRepository;
import uk.ac.leedsbeckett.library.exception.AccountNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountModelAssembler accountAssembler;


    public AccountService(AccountRepository accountRepository, AccountModelAssembler accountAssembler) {
        this.accountRepository = accountRepository;
        this.accountAssembler = accountAssembler;
    }

    public EntityModel<Account> getAccountByIdJson (Long id) {
        Account account = accountRepository.findAccountById(id);
        if(account == null){
            throw new AccountNotFoundException(id);
        }
        return accountAssembler.toModel(account);
    }

    public CollectionModel<EntityModel<Account>> getAllAccounts() {
        List<EntityModel<Account>> accounts = accountRepository.findAll()
                .stream()
                .map(accountAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).all()).withSelfRel());
    }

    public EntityModel<Account> getAccountByStudentId(String studentId) {
        Account studentAccount = accountRepository.findAccountByStudentId(studentId);
        if (studentAccount == null) {
            throw new AccountNotValidException(studentId);
        }
        return accountAssembler.toModel(studentAccount);
    }

    public ResponseEntity<?> createNewAccount(Account newAccount) {
        if (newAccount.getStudentId() == null || newAccount.getStudentId().isEmpty()) {
            throw new RuntimeException("Error");
        }
        Account savedAccount;
        try {
            savedAccount = accountRepository.save(newAccount);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("An account already exists for student ID " + newAccount.getStudentId() + ".");
        }
        EntityModel<Account> entityModel = accountAssembler.toModel(savedAccount);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> updateOrCreateAccount(Account newAccount, Long id) {
        Account updatedAccount = accountRepository.findById(id)
                .map(account -> {
                    account.setStudentId(newAccount.getStudentId());
                    return accountRepository.save(account);
                })
                .orElseGet(() -> {
                    newAccount.setId(id);
                    return accountRepository.save(newAccount);
                });
        EntityModel<Account> entityModel = accountAssembler.toModel(updatedAccount);
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        accountRepository.delete(account);
        return ResponseEntity.noContent().build();
    }

}
