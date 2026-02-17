package uz.lib.library.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.AuthorDto;
import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.CategoryDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.service.impl.CategoryServiceImpl;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryResources {
    private final CategoryServiceImpl categoryService;
    @GetMapping("/{id}")
    public ResponseDto<Page<BookDto>> get(@PathVariable Integer id,
                                          @RequestParam(required = false) String sorting,
                                          @RequestParam(required = false) String ordering,
                                          @RequestParam(required = false) List<String> filter,
                                          @RequestParam(required = false,defaultValue = "10") Integer size,
                                          @RequestParam(required = false, defaultValue = "0") Integer currentPage){
        return categoryService.getWithSort(id, filter,sorting,ordering,currentPage);
    }
    @PostMapping
    public ResponseDto<CategoryDto> addCategory(@RequestBody @Valid CategoryDto categoryDto){
        return categoryService.addCategory(categoryDto);
    }
    @GetMapping("/{id}/authors")
    public ResponseDto<Set<AuthorDto>> books(@PathVariable Integer id){
        return categoryService.bookByCategory(id);
    }
}
