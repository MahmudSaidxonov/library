package uz.lib.library.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.lib.library.model.Authorities;
import uz.lib.library.service.additional.AppStatusMessages;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
//    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
//    @Size(min = 9, max = 9, message = AppStatusMessages.VALIDATION_ERROR)
//    private String passport;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String phoneNumber;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String firstName;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String lastName;
    private String middleName;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    @Email(message = AppStatusMessages.NOT_VALID_EMAIL)
    private String email;
    private String gender;
    private Date birthDate;
    @NotBlank(message = AppStatusMessages.EMPTY_STRING)
    private String password;
    private List<Authorities> roles;

}
