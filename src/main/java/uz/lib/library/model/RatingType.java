package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "rating_type")
@Entity
@Getter
@Setter
public class RatingType {
    @Id
    @GeneratedValue(generator = "ratingIdSeq")
    @SequenceGenerator(name = "ratingIdSeq", sequenceName = "rating_id_seq", allocationSize = 1)
    private Integer id;
    private String type;
//    @OneToMany(mappedBy = "ratingType", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Rating> ratingList = new ArrayList<>();
}
