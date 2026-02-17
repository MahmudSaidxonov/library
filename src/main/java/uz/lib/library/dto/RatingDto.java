package uz.lib.library.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.lib.library.model.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDto {
    private Integer id;
    private Integer diploma_ball;
    private Integer certificates_ball;
    private Integer text_ball;
    private Integer total;
    @JsonIgnore
    @OneToOne
    private User userId;
}
