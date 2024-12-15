package homework12._2.Am.homework12._2.Service.impl;

import homework12._2.Am.homework12._2.Service.IEmployeeService;
import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.exception.ErrorCode;
import homework12._2.Am.homework12._2.modal.Employee;
import homework12._2.Am.homework12._2.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeService implements IEmployeeService {
    IEmployeeRepository employeeRepository;
    @Override
    public List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest) {
        return employeeRepository.findByAttributes(employeeSearchRequest);
    }

    public Optional<Employee> findById(UUID id){
        return employeeRepository.findById(id);
    }

    @Override
    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

   @Override
   public void delete(UUID id) {

       employeeRepository.delete(id); // Sử dụng phương thức mặc định deleteById
   }

}
