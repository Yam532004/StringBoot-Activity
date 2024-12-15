package homework12._2.Am.homework12._2.modal;

import homework12._2.Am.homework12._2.emums.Gender;
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
public class Employee {
    private UUID id ;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Double salary;
    private String phone;

    Integer departmentId;

    public Employee(UUID id, String name, LocalDate dob, Gender gender, Double salary, String phone) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.salary = salary;
        this.phone = phone;
    }

}

