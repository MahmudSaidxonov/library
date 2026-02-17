package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "rating")
@Entity
@Getter
@Setter
public class Rating {
    @Id
    @GeneratedValue(generator = "ratingIdSeq")
    @SequenceGenerator(name = "ratingIdSeq", sequenceName = "rating_id_seq", allocationSize = 1)
    private Integer id;
//    @Column(columnDefinition = "integer default 0")
    private Integer diploma_ball;
//    @Column(columnDefinition = "integer default 0")
    private Integer certificates_ball;
//    @Column(columnDefinition = "integer default 0")
    private Integer text_ball;
//    private Integer test_ball;
//    @Column(columnDefinition = "integer default 0")
    private Integer total;
    @OneToOne
    private User userId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private RatingType ratingType;
}
