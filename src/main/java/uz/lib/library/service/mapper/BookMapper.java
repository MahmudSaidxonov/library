package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import uz.lib.library.dto.BookDto;
import uz.lib.library.model.Book;

@Mapper(componentModel = "spring")
public abstract class BookMapper implements CommonMapper<BookDto, Book> {
    @Autowired
    protected CategoryMapper categoryMapper;
//    @Autowired
//    protected ProductVariantMapper productVariantMapper;

    @Mapping(target = "category", expression = "java(categoryMapper.toDto(book.getCategory()))")
    public abstract BookDto toDto(Book book);
    @Mapping(target = "category", expression = "java(categoryMapper.toEntity(bookDto.getCategory()))")
    @Mapping(target = "isAvailable", expression = "java(true)")
    public abstract Book toEntity(BookDto bookDto);

}
