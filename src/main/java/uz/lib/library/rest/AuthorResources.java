package uz.lib.library.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.lib.library.dto.AuthorDto;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.UserDto;
import uz.lib.library.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("author")
@RequiredArgsConstructor
public class AuthorResources {

    private  final AuthorService authorService;

    @GetMapping()
    public ResponseDto<List<AuthorDto>> getAllAuthors(){
        return authorService.getAllAuthors();
    }

}
