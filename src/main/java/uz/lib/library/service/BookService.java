package uz.lib.library.service;

import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.projections.BookProjection;

import java.util.List;

public interface BookService {
    ResponseDto<BookDto> addBook(BookDto bookDto);
    ResponseDto<BookDto> updateBook(BookDto bookDto);
    ResponseDto<BookDto> getBookById(Integer id);
    ResponseDto<List<BookProjection>> getBooks(Integer currentPage, Integer size);

}
