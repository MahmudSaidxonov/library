package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "bookIdSeq")
    @SequenceGenerator(name = "bookIdSeq", sequenceName = "book_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String description;
    private String download;
    @ManyToOne
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_author")
    private Author author;

    private Boolean isAvailable;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime date;
}
