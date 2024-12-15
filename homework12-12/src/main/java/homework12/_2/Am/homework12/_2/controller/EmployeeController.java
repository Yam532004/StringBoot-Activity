package homework12._2.Am.homework12._2.controller;


import homework12._2.Am.homework12._2.Service.IEmployeeService;
import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.repository.IEmployeeRepository;
import homework12._2.Am.homework12._2.util.JsonResponse;
import homework12._2.Am.homework12._2.exception.AppException;
import homework12._2.Am.homework12._2.exception.ErrorCode;
import homework12._2.Am.homework12._2.modal.Employee;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/employees")
public class EmployeeController {

    IEmployeeService employeeService;
    @GetMapping
    public ResponseEntity<?> getEmployees(EmployeeSearchRequest employeeSearchRequest) {
        return JsonResponse.ok(employeeService.findByAttributes(employeeSearchRequest));
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
        // Kiểm tra nhân viên có tồn tại hay không
        employeeService.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));

        // Xóa nhân viên
        employeeService.delete(id);

        // Trả về phản hồi
        return JsonResponse.noContent();
    }

}
