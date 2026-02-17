package uz.lib.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.lib.library.model.Question;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Integer id;
    private String title;
    private String description;
    private String maxMarks;
    private String numberOfQuestions;
    private Boolean isActive;
//    @JsonIgnore
//    private List<QuestionDto> questions = new ArrayList<>();

}
