package uz.lib.library.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static uz.lib.library.service.additional.AppStatusMessages.EMPTY_STRING;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;
    @NotBlank(message = EMPTY_STRING)
    private String name;
    @NotBlank(message = EMPTY_STRING)
    private String description;
    @NotBlank(message = EMPTY_STRING)
    private String download;
    private Boolean isAvailable;
    private AuthorDto authorDto;
    private CategoryDto category;

}
