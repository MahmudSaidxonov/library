package uz.lib.library.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.lib.library.dto.UserDto;
import uz.lib.library.model.User;
import uz.lib.library.repository.UserRepository;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UserDto, User> {

    @Autowired
    protected PasswordEncoder encoder;

    @Autowired
    protected UserRepository userRepository;

    public Optional<User> toEditEntity(UserDto dto){
        return userRepository.findById(dto.getId())
                .map(u -> {
                    if (dto.getEmail() != null) u.setEmail(dto.getEmail());
                    if (dto.getFirstName() != null) u.setFirstName(dto.getFirstName());
                    if (dto.getLastName() != null) u.setLastName(dto.getLastName());
                    if (dto.getMiddleName() != null) u.setMiddleName(dto.getMiddleName());
                    if (dto.getGender() != null) u.setGender(dto.getGender());
                    if (dto.getBirthDate() != null) u.setBirthDate(dto.getBirthDate());
                    if (dto.getPhoneNumber() != null) u.setPhoneNumber(dto.getPhoneNumber());
                    return u;
                });
    }

    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    @Mapping(target = "isActive", expression = "java(true)")
    @Mapping(target = "password", expression = "java(encoder.encode(userDto.getPassword()))")
    public abstract User toEntity(UserDto userDto);

    @Mapping(target = "password", expression = "java(null)")
    @Mapping(target = "birthDate", dateFormat = "dd.MM.yyyy")
    public abstract UserDto toDto(User user);
}
