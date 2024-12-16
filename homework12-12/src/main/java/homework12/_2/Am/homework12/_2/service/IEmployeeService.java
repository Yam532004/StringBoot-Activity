package homework12._2.Am.homework12._2.service;

import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.entity.Employee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {
    // Tìm kiếm danh sách nhân viên dựa trên các tiêu chí
    List<Employee> findByAttr(String name, Gender gender, LocalDate dob, Double minSalary, Double maxSalary, Department department);

    // Tìm nhân viên theo ID
    Optional<Employee> findById(UUID id);

    // Lưu thông tin nhân viên
    Employee save(Employee employee);

    // Xóa nhân viên theo ID
    void delete(UUID id);


}
