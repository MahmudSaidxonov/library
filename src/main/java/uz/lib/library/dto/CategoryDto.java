package uz.lib.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.lib.library.model.Category;

import java.util.List;
import java.util.Set;

import static uz.lib.library.service.additional.AppStatusMessages.EMPTY_STRING;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    private Integer parentId;
//    private List<CategoryDto> parentCategoryId;
//    private Set<Category> childCategories;

}
