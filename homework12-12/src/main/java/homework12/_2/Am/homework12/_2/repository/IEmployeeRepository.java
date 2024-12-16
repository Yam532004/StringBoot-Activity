package homework12._2.Am.homework12._2.repository;

import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:gender IS NULL OR e.gender = :gender) AND " +
            "(:dob IS NULL OR e.dob = :dob) AND " +
            "(:minSalary IS NULL OR e.salary >= :minSalary) AND " +
            "(:maxSalary IS NULL OR e.salary <= :maxSalary) AND " +
            "(:department IS NULL OR e.department = :department)")
    Page<Employee> findByAttr(@Param("name") String name,
                              @Param("gender") Gender gender,
                              @Param("dob") LocalDate dob,
                              @Param("minSalary") Double minSalary,
                              @Param("maxSalary") Double maxSalary,
                              @Param("department") Department department,
                              Pageable pageable);
Optional<Employee> findById(UUID id);

        Employee save(Employee employee);

        Page<Employee> findAll(Pageable pageable);

    }
