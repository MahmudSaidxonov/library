package uz.lib.library.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.lib.library.dto.BookDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.projections.BookProjection;
import uz.lib.library.service.BookService;

import java.util.List;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookResources {

    private final BookService bookService;

    @PostMapping
    public ResponseDto<BookDto> addProduct(@RequestBody @Valid BookDto bookDto){
        return bookService.addBook(bookDto);
    }

    @PatchMapping
    public ResponseDto<BookDto> updateProduct(@RequestBody BookDto bookDto){
        return bookService.updateBook(bookDto);
    }
    @GetMapping
    public ResponseDto<List<BookProjection>> getProducts(@RequestParam(required = false) Integer currentPage,
                                                         @RequestParam(required = false) Integer size){
        return bookService.getBooks(currentPage,size);
    }
    @GetMapping("/{id}")
    public ResponseDto<BookDto> getProductById(@PathVariable Integer id){
        return bookService.getBookById(id);
    }

//    @GetMapping("viewed")
//    public ResponseDto<List<BookProjection>> getViewedProduct(@RequestParam Integer userId){
//        return bookService.getViewedBooks(userId);
//    }
}
