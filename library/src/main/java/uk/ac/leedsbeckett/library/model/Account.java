package uk.ac.leedsbeckett.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Account {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    private String studentId;
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(
            name = "book_student",
            joinColumns = @JoinColumn(name = "studentID"),
            inverseJoinColumns = @JoinColumn(name = "isbn")
    )

    @JsonIgnore
    private List<Book> booksList = new ArrayList<>();
    public Account() {
    }

    public Account(String studentId) {
        this.studentId = studentId;
    }
}