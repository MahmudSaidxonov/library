package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.AuthorDto;
import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.CategoryDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Book;
import uz.lib.library.repository.BookRepository;
import uz.lib.library.repository.CategoryRepository;
import uz.lib.library.service.CategoryService;
import uz.lib.library.service.mapper.BookMapper;
import uz.lib.library.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final BookMapper bookMapper;
    private final CategoryMapper categoryMapper;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ResponseDto<CategoryDto> addCategory(CategoryDto categoryDto) {
        try {
            return ResponseDto.<CategoryDto>builder()
                    .data(categoryMapper.toDto(
                            categoryRepository.save(
                                    categoryMapper.toEntity(categoryDto)
                            )
                    ))
                    .message(OK)
                    .success(true)
                    .build();
        }catch (Exception e){
            return ResponseDto.<CategoryDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + " : " + e.getMessage())
                    .data(categoryDto)
                    .build();
        }
    }

    @Override
    public ResponseDto<Page<BookDto>> getWithSort(Integer id, List<String> filter, String sorting, String ordering, Integer currentPage) {
        Page<Book> sort = bookRepository.getWithSort(id,filter, sorting, ordering, currentPage);
        if(sort.isEmpty()){
            return ResponseDto.<Page<BookDto>>builder()
                    .message(EMPTY_STRING)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        }
        return ResponseDto.<Page<BookDto>>builder()
                .data(sort.map(bookMapper::toDto))
                .code(OK_CODE)
                .message(OK)
                .build();
    }

    public ResponseDto<Set<AuthorDto>> bookByCategory(Integer categoryId){

        ResponseDto<Page<BookDto>> pageResponseDto = getWithSort(categoryId, null,null,null,0);
        Page<BookDto> data = pageResponseDto.getData();
        if(!data.isEmpty()) {
            Set<AuthorDto> collect = data.getContent()
                    .stream()
                    .map(p -> p.getAuthorDto())
                    .collect(Collectors.toSet());
            return ResponseDto.<Set<AuthorDto>>builder()
                    .data(collect)
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .build();
        }
        return ResponseDto.<Set<AuthorDto>>builder()
                .message(EMPTY_STRING)
                .build();
    }

}
