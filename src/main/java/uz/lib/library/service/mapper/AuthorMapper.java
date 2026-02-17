package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import uz.lib.library.dto.AuthorDto;
import uz.lib.library.model.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper extends CommonMapper<AuthorDto, Author> {

}
