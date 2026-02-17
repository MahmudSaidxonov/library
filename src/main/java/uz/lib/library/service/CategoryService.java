package uz.lib.library.service;

import org.springframework.data.domain.Page;
import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.CategoryDto;
import uz.lib.library.dto.ResponseDto;

import java.util.List;

public interface CategoryService {
    ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto);
    ResponseDto<Page<BookDto>> getWithSort(Integer id, List<String> filter, String sorting, String ordering, Integer currentPage);

}
