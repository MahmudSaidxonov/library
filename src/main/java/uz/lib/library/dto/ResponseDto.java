package uz.lib.library.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto<T> {
    private int code;
    private String message;
    private boolean success;
    private T data;
//    private List<ErrorDto> errors;
}
