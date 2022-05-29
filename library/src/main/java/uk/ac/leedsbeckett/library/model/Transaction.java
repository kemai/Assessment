package uk.ac.leedsbeckett.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Transaction {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    private String studentId;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Book> bookList = new ArrayList<>();
    private LocalDate borrowDate;
    private LocalDate returnDate;


    public Transaction() {
    }

    public Transaction(String studentId) {
        this.studentId = studentId;
    }
}