package uz.lib.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "question")
@Entity
@Getter
@Setter
public class Question {

    @Id
    @GeneratedValue(generator = "questionIdSeq")
    @SequenceGenerator(name = "questionIdSeq", sequenceName = "question_id_seq", allocationSize = 1)
    private Integer id;
    @Column(length = 5000)
    private String content;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    @Transient
    private String givenAnswer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

}
