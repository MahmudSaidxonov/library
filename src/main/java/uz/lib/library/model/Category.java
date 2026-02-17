package uz.lib.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(generator = "categoryIdSeq")
    @SequenceGenerator(name = "categoryIdSeq", sequenceName = "category_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private Integer parentId;

//    @OneToMany(mappedBy = "id")
//    private Set<Category> childCategories;
//    @OneToMany
//    @JoinColumn(name = "parent_category_id")
//    private List<Category> parentCategoryId;

}
