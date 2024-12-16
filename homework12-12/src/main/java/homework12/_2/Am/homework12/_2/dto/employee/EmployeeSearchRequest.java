package homework12._2.Am.homework12._2.dto.employee;

import homework12._2.Am.homework12._2.emums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeSearchRequest {
     String name;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
     LocalDate dobFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
     LocalDate dobTo;
     Gender gender;
     Integer salaryRange;
     String phone;
     Integer department_id;
}
