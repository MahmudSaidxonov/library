package uz.lib.library.service;

import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.TokenDto;
import uz.lib.library.dto.UserDto;

import java.util.List;

public interface UserService {
    ResponseDto<UserDto> addUser(UserDto usersDto);
    ResponseDto<UserDto> getUserById(Integer id);

    ResponseDto<List<UserDto>> getAllUsers();

    ResponseDto<UserDto> updateUser(UserDto usersDto);

    ResponseDto<UserDto> deleteUserById(Integer id);

    ResponseDto<UserDto> getUserByPhoneNumber(String phoneNumber);

//    UsersDto loadUserByUsername(String username) throws UsernameNotFoundException;

    ResponseDto<String> getToken(TokenDto getTokenDto);
}
