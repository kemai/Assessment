package uk.ac.leedsbeckett.library.model;

import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data
@Entity
public class Student {
    private Long id;
    private String Surname;
    private String Firstname;
    private String externalStudentId;
    private String financeAccount;
    @ManyToMany(mappedBy = "bookLoanedByStudent")
    @JsonIgnore
    Set<Book>  studentLoanBook;
}
