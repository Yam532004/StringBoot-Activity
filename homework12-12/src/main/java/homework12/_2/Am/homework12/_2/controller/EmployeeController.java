package homework12._2.Am.homework12._2.controller;


import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.service.IEmployeeService;
import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.util.JsonResponse;
import homework12._2.Am.homework12._2.exception.AppException;
import homework12._2.Am.homework12._2.exception.ErrorCode;
import homework12._2.Am.homework12._2.entity.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/employees")
public class EmployeeController {

    IEmployeeService employeeService;
    @GetMapping
    public ResponseEntity<?> getEmployees(String name, Gender gender, LocalDate dob, Double minSalary, Double maxSalary, Department department_id) {
        return JsonResponse.ok(employeeService.findByAttr(name, gender, dob, minSalary, maxSalary, department_id));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeByID(@PathVariable("id") UUID id) {
        return employeeService.findById(id)
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Employee employee) {

        return JsonResponse.created(employeeService.save(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Employee employee) {
        // Kiểm tra nhân viên có tồn tại không
        Employee existingEmployee = employeeService.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));

        // Cập nhật thông tin nhân viên
        employee.setId(existingEmployee.getId()); // Đảm bảo sử dụng ID cũ
        Employee updatedEmployee = employeeService.save(employee);

        // Trả về kết quả
        return ResponseEntity.ok(updatedEmployee);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        // Xóa nhân viên
        employeeService.delete(id);

        // Trả về phản hồi
        return JsonResponse.noContent();
    }

}
