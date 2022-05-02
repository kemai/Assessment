package uk.ac.leedsbeckett.student.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double fee;
    @ManyToMany(mappedBy = "coursesEnrolledIn")
   // @JsonIgnore
    Set<Student> studentsEnrolledInCourse;
}
