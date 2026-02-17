package uz.lib.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "quiz")
@Entity
@Getter
@Setter
public class Quiz {
    @Id
    @GeneratedValue(generator = "quizIdSeq")
    @SequenceGenerator(name = "quizIdSeq", sequenceName = "quiz_id_seq", allocationSize = 1)
    private Integer id;
    private String title;
    @Column(length = 5000)
    private String description;
    private String maxMarks;
    private String numberOfQuestions;
    private Boolean isActive;
//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL,orphanRemoval = true)
//    private List<Question> questions = new ArrayList<>();

}
