package homework12._2.Am.homework12._2.dto.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public enum ErrorCode {
    EMPLOYEE_NOT_EXIST(40401, "Employee does not exist", HttpStatus.NOT_FOUND);
    Integer code;
    String message;
    HttpStatus status;
}
