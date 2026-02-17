package uz.lib.library.service.impl;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.lib.library.dto.ResponseDto;
import uz.lib.library.dto.TokenDto;
import uz.lib.library.dto.UserDto;
import uz.lib.library.model.Authorities;
import uz.lib.library.model.Rating;
import uz.lib.library.model.User;
import uz.lib.library.repository.AuthoritiesRepository;
import uz.lib.library.repository.RatingRepository;
import uz.lib.library.repository.UserRepository;
import uz.lib.library.security.JwtService;
import uz.lib.library.service.UserService;
import uz.lib.library.service.mapper.UserMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static uz.lib.library.service.additional.AppStatusCodes.*;
import static uz.lib.library.service.additional.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthoritiesRepository authoritiesRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final UserMapper userMapper;
    private final Gson gson;

    @Override
    public ResponseDto<UserDto> addUser(UserDto usersDto) {
        try {
            Optional<User> byEmail = userRepository.findByEmail(usersDto.getEmail());
            Optional<User> byPhoneNumber = userRepository.findFirstByPhoneNumber(usersDto.getPhoneNumber());

            if (byEmail.isPresent())
                return ResponseDto.<UserDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message("User with this email " + usersDto.getEmail() + " already exists!")
                        .build();

            if (byPhoneNumber.isPresent())
                return ResponseDto.<UserDto>builder()
                        .code(VALIDATION_ERROR_CODE)
                        .message("User with this phone number " + usersDto.getPhoneNumber() + " already exists!")
                        .build();

            User user = userMapper.toEntity(usersDto);
            userRepository.save(user);

            return ResponseDto.<UserDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(userMapper.toDto(user))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .data(usersDto)
                    .code(DATABASE_ERROR_CODE)
                    .message("Error while saving user: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> getUserById(Integer id) {
        try {
            Optional<User> byId = userRepository.findById(id);
            if (byId.isEmpty()) {
                return ResponseDto.<UserDto>builder()
                        .code(NOT_FOUND_ERROR_CODE)
                        .message(NOT_FOUND)
                        .build();
            }
            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .message(OK)
                    .code(OK_CODE)
                    .data(userMapper.toDto(byId.get()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<List<UserDto>> getAllUsers() {
        try {
            return ResponseDto.<List<UserDto>>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .data(userRepository.findAllByIsActive(true).stream()
                            .map(userMapper::toDto)
                            .collect(Collectors.toList()))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<UserDto>>builder()
                    .code(1)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> updateUser(UserDto userDto) {
        if (userDto.getId() == null) {
            return ResponseDto.<UserDto>builder()
                    .message("UserID is null")
                    .code(-2)
                    .data(userDto)
                    .build();
        }

        Optional<User> byEmail = userRepository.findByEmail(userDto.getEmail());
        Optional<User> byPhoneNumber = userRepository.findFirstByPhoneNumber(userDto.getPhoneNumber());

        if (byEmail.isPresent())
            return ResponseDto.<UserDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("User with this email " + userDto.getEmail() + " already exists!")
                    .build();

        if (byPhoneNumber.isPresent())
            return ResponseDto.<UserDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message("User with this phone number " + userDto.getPhoneNumber() + " already exists!")
                    .build();

        Optional<User> userOptional = userRepository.findById(userDto.getId());

        if (userOptional.isEmpty()) {
            return ResponseDto.<UserDto>builder()
                    .message("User with ID " + userDto.getId() + " is not found")
                    .code(NOT_FOUND_ERROR_CODE)
                    .data(userDto)
                    .build();
        }


//        User user = getUpdatedUser(userDto, userOptional.get());
//        Optional<User> user1 = userMapper.toEditEntity(userDto);
        Optional<User> user = userMapper.toEditEntity(userDto);

        try {
            userRepository.save(user.get());

            return ResponseDto.<UserDto>builder()
                    .data(userMapper.toDto(user.get()))
                    .success(true)
                    .message("OK")
                    .build();
        } catch (Exception e) {
            return ResponseDto.<UserDto>builder()
                    .data(userMapper.toDto(user.get()))
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + ": " + e.getMessage())
                    .build();
        }
    }

//    private static User getUpdatedUser(UserDto userDto, User user) {
//        user.setEmail(Optional.ofNullable(userDto.getEmail()).orElse(user.getEmail()));
//        user.setGender(Optional.ofNullable(userDto.getGender()).orElse(user.getGender()));
//        user.setFirstName(Optional.ofNullable(userDto.getFirstName()).orElse(user.getFirstName()));
//        user.setLastName(Optional.ofNullable(userDto.getLastName()).orElse(user.getLastName()));
//        user.setMiddleName(Optional.ofNullable(userDto.getMiddleName()).orElse(user.getMiddleName()));
//        user.setBirthDate(Optional.ofNullable(userDto.getBirthDate()).orElse(user.getBirthDate()));
//        user.setPhoneNumber(Optional.ofNullable(userDto.getPhoneNumber()).orElse(user.getPhoneNumber()));
//        return user;
//    }

    @Override
    public ResponseDto<UserDto> deleteUserById(Integer id) {
        Optional<User> user=userRepository.findByIdAndIsActive(id, true);
        if(user.isEmpty()) {
            return (ResponseDto.<UserDto>builder()
                    .message(NOT_FOUND)
                    .code(NOT_FOUND_ERROR_CODE)
                    .build());
        }
        User delUser = user.get();
        delUser.setIsActive(false);
        try {
            userRepository.save(delUser);
            return ResponseDto.<UserDto>builder()
                    .success(true)
                    .message(OK)
                    .data(userMapper.toDto(delUser))
                    .build();

        }catch (Exception e){
            return ResponseDto.<UserDto>builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(OK_CODE)
                    .build();
        }
    }

    @Override
    public ResponseDto<UserDto> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findFirstByPhoneNumberAndIsActive(phoneNumber, true)
                .map(u -> ResponseDto.<UserDto>builder()
                        .data(userMapper.toDto(u))
                        .success(true)
                        .message("OK")
                        .build())
                .orElse(ResponseDto.<UserDto>builder()
                        .message("User with phone number " + phoneNumber + " is not found")
                        .code(-1)
                        .build());
    }

    @Override
    public ResponseDto<String> getToken(TokenDto tokenDto) {
        Optional<User> user = userRepository.findByEmail(tokenDto.getEmail());

        if (user.isEmpty()) {
            ResponseDto.<String>builder()
                    .code(1)
                    .message("Email or password is incorrect")
                    .build();
        }
        User users = user.get();
        if(!passwordEncoder.matches(tokenDto.getPassword(), user.get().getPassword())){
            return ResponseDto.<String>builder()
                    .message("Password is not correct")
                    .code(VALIDATION_ERROR_CODE)
                    .build();
        }

        users = loadUserWithRoles(users);
        return ResponseDto.<String>builder()
                .success(true)
                .message("OK")
                .data(jwtService.generateToken(gson.toJson(users), users.getRoles().stream().map(Authorities::getName).collect(Collectors.toList())))
                .build();
    }
    public User loadUserWithRoles(User u) throws UsernameNotFoundException {
        Optional<List<Authorities>> list = authoritiesRepository.getAuthoritiesByUserId(u.getId());
        if (list.isEmpty()) throw new UsernameNotFoundException("user roles is not found");
        u.setRoles(list.get());
        return u;
    }
}