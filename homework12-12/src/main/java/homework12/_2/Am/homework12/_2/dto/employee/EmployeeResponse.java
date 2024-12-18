package homework12._2.Am.homework12._2.dto.employee;

import homework12._2.Am.homework12._2.dto.department.DepartmentResponse;
import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
    private int id ;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Double salary;
    private String phone;

    /* @ManyToOne
    @JoinColumn(name = "department_id", nullable = false) */
    DepartmentResponse department;
}
