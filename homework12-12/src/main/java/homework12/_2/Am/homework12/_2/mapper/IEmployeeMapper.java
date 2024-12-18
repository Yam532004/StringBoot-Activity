package homework12._2.Am.homework12._2.mapper;

import homework12._2.Am.homework12._2.dto.employee.EmployeeRequest;
import homework12._2.Am.homework12._2.dto.employee.EmployeeResponse;
import homework12._2.Am.homework12._2.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface IEmployeeMapper {
    @Mapping(target = "id", ignore = true)
    Employee employeeRequestToEmployee(EmployeeRequest employeeRequest);
    EmployeeResponse employeeToEmployeeResponse(Employee employee);
}
