package uz.lib.library.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.lib.library.Exceptions.DatabaseException;
import uz.lib.library.dto.AuthorDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.UserDto;
import uz.lib.library.model.Author;
import uz.lib.library.repository.AuthorRepository;
import uz.lib.library.service.AuthorService;
import uz.lib.library.service.mapper.AuthorMapper;

import java.util.List;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.OK_CODE;
import static uz.lib.library.service.additional.AppStatusMessages.DATABASE_ERROR;
import static uz.lib.library.service.additional.AppStatusMessages.OK;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;
    @Override
    public Author addAuthor(String name) {
        Author author = new Author();
        author.setName(name);

//        authorRepository.addIfNewBrand(author.getName());
//        return author;
        return authorRepository.addIfNewAuthor(name)
                .orElseThrow(() -> new DatabaseException("Could not save author to database!"));
    }

    @Override
    public ResponseDto<List<AuthorDto>> getAllAuthors() {
       try {
            return ResponseDto.<List<AuthorDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(authorRepository.findAllAuthors().stream().map(authorMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<AuthorDto>>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }
}
