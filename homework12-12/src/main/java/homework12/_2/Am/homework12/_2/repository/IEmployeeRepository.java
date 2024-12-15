package homework12._2.Am.homework12._2.repository;

import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.modal.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository {
    List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest);

    Optional<Employee> findById(UUID id);

    Employee save(Employee employee);

    void delete(UUID id);
}
