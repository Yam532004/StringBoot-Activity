package homework12._2.Am.homework12._2.service.impl;

import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.service.IEmployeeService;
import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.entity.Employee;
import homework12._2.Am.homework12._2.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {
    IEmployeeRepository employeeRepository;

    public List<Employee> findByAttr(String name, Gender gender, LocalDate dob, Double minSalary, Double maxSalary, Department department) {
        return employeeRepository.findByAttr(name, gender, dob, minSalary, maxSalary, department);
    }

    @Override
    public Optional<Employee> findById(UUID id){
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }
    @Override
    public void delete(UUID id) {
        employeeRepository.deleteById(id);
    }

}
