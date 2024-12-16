package homework12._2.Am.homework12._2.entity;

import homework12._2.Am.homework12._2.emums.Gender;
import jakarta.persistence.*;
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
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id ;
    private String name;
    private LocalDate dob;
    private Gender gender;
    private Double salary;
    private String phone;

    @ManyToOne
    Department department;
}

