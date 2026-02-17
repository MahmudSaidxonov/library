package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Author;
import uz.lib.library.model.Book;
import uz.lib.library.projections.BookProjection;
import uz.lib.library.repository.BookRepository;
import uz.lib.library.service.AuthorService;
import uz.lib.library.service.BookService;
import uz.lib.library.service.mapper.BookMapper;
import uz.lib.library.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorServiceImpl authorService;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;
    @Override
    public ResponseDto<BookDto> addBook(BookDto bookDto) {
        Author author = authorService.addAuthor(bookDto.getAuthorDto().getName());
        Book book = bookMapper.toEntity(bookDto);
        book.setAuthor(author);
        try {
            Book save = bookRepository.save(book);

            return ResponseDto.<BookDto>builder()
                    .success(true)
                    .data(bookMapper.toDto(save))
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> updateBook(BookDto bookDto) {
        if (bookDto.getId() == null) {
            return ResponseDto.<BookDto>builder()
                    .message("Book ID is null")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        Optional<Book> optional = bookRepository.findById(bookDto.getId());

        if (optional.isEmpty()) {
            return ResponseDto.<BookDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        Book book = optional.get();

        if (bookDto.getName() != null) {
            book.setName(bookDto.getName());
        }
        if (bookDto.getDescription() != null) {
            book.setDescription(bookDto.getDescription());
        }
        if (bookDto.getCategory() != null) {
            book.setCategory(categoryMapper.toEntity(bookDto.getCategory()));
        }

        try {
            bookRepository.save(book);

            return ResponseDto.<BookDto>builder()
                    .message(OK)
                    .data(bookMapper.toDto(book))
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> getBookById(Integer id) {
//        Integer userId = 1;
        try {
            Optional<Book> byId = bookRepository.findById(id);
            if (!byId.isEmpty()) {
//                bookRepository.insertViewedProduct(userId, id);
                return ResponseDto.<BookDto>builder()
                        .data(bookMapper.toDto(byId.get()))
                        .success(true)
                        .code(OK_CODE)
                        .message(OK)
                        .build();
            }
            return ResponseDto.<BookDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .message(DATABASE_ERROR)
                    .code(DATABASE_ERROR_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<BookProjection>> getBooks(Integer currentPage, Integer size) {
        try {
            List<BookProjection> products = bookRepository.getBooks(currentPage,size);
            return ResponseDto.<List<BookProjection>>builder()
                    .success(true)
                    .code(OK_CODE)
                    .message(OK)
                    .data(products)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<BookProjection>>builder()
                    .success(false)
                    .code(OK_CODE)
                    .message(e.getMessage())
                    .build();
        }
    }
}
