package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "author")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "authorIdSequence")
    @SequenceGenerator(name = "authorIdSequence", sequenceName = "author_id_seq", allocationSize = 1)
    private Integer id;
    @Column(unique=true)
    private String name;
    private LocalDateTime createdAt;
//    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Book> books = new ArrayList<>();
//    @OneToMany(cascade = CascadeType.ALL)
//    private List<Book> books;
}