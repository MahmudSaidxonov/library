package uz.lib.library.service;

import uz.lib.library.dto.AuthorDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.model.Author;

import java.util.List;

public interface AuthorService {
    Author addAuthor(String name);

    ResponseDto<List<AuthorDto>> getAllAuthors();
}
