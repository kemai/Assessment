package uk.ac.leedsbeckett.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "isbn")
    private String isbn;
    @Column(unique = true)
    private String Author;
    private String Title;
    private Long year;
    private Integer n_copies;


    @ManyToMany(mappedBy = "booksList")
    @JsonIgnore
    Set<Account> accountsBookList;
    @ManyToOne
    @ToString.Exclude
    private Transaction transaction;

    @JsonProperty
    public String getStudentId() {
        return transaction.getStudentId();
    }

    @JsonProperty
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    @JsonIgnore
    public Transaction getTransaction() {
        return this.transaction;
    }



}
