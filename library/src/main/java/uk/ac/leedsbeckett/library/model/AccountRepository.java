package uk.ac.leedsbeckett.library.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findAccountByStudentId (String studentId);
    Account findAccountById(Long id);
}
