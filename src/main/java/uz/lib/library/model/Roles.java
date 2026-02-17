package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    @Id
    @GeneratedValue(generator = "roleIdSequence")
    @SequenceGenerator(name = "roleIdSequence", sequenceName = "role_id_seq", allocationSize = 1)
    private Integer id;
    private Integer userId;
    private Integer authorityId;

    public Roles(Integer userId, Integer authorityId) {
        this.userId = userId;
        this.authorityId = authorityId;
    }
}
