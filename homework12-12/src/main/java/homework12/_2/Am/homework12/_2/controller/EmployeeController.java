package homework12._2.Am.homework12._2.controller;


import homework12._2.Am.homework12._2.dto.ApiResponse;
import homework12._2.Am.homework12._2.dto.page.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public ResponseEntity<ApiResponse<PageResponse<Employee>>> getEmployees(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) Department department,
            @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // Gọi service để lấy dữ liệu
        Page<Employee> employees = employeeService.findByAttr(name, gender, dob, minSalary, maxSalary, department, pageable);
        if (employees.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.<PageResponse<Employee>>builder()
                    .data(new PageResponse<>(employees))
                    .message("No employees found with the given criteria")
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.<PageResponse<Employee>>builder()
                .data(new PageResponse<>(employees))
                .build());
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
