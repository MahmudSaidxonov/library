package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import uz.lib.library.dto.CategoryDto;
import uz.lib.library.model.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends CommonMapper<CategoryDto, Category>{
}
