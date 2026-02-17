package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities")
@Getter
@Setter
public class Authorities {
    @Id
    @GeneratedValue(generator = "authIdSequence")
    @SequenceGenerator(name = "authIdSequence", sequenceName = "auth_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
}
