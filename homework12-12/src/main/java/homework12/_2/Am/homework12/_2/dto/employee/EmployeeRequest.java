package homework12._2.Am.homework12._2.dto.employee;

import homework12._2.Am.homework12._2.dto.department.DepartmentRequest;
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
public class EmployeeRequest {
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Double salary;
    private String phone;

    private DepartmentRequest department;  // DepartmentRequest chứa id
    /* @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    DepartmentRequest department; */
}
