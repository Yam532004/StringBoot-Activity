package homework12._2.Am.homework12._2.controller;


import homework12._2.Am.homework12._2.dto.ApiResponse;
import homework12._2.Am.homework12._2.dto.employee.EmployeeRequest;
import homework12._2.Am.homework12._2.dto.employee.EmployeeResponse;
import homework12._2.Am.homework12._2.dto.page.PageResponse;
import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.mapper.IEmployeeMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/employees")
public class EmployeeController {

    IEmployeeService employeeService;
    IEmployeeMapper employeeMapper;
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<EmployeeResponse>>> getEmployees(
            @RequestParam(defaultValue = "", required = false) String name,
            @RequestParam(required = false) Gender gender,
            @RequestParam(required = false) LocalDate dob,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) Department department,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        // Gọi service để lấy dữ liệu
        Page<Employee> employees = employeeService.findByAttr(name, gender, dob, minSalary, maxSalary, department, pageable);
        Page<EmployeeResponse> employeeResponses = employees.map(employeeMapper::employeeToEmployeeResponse);

        if (employeeResponses.isEmpty()) {
            return ResponseEntity.ok(ApiResponse.<PageResponse<EmployeeResponse>>builder()
                    .data(new PageResponse<>(employeeResponses))
                    .message("No employees found with the given criteria")
                    .build());
        }
        return ResponseEntity.ok(ApiResponse.<PageResponse<EmployeeResponse>>builder()
                .data(new PageResponse<>(employeeResponses))
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getEmployeeByID(@PathVariable("id") int id) {
       Optional<Employee> employee = employeeService.findById(id);

       if (employee.isEmpty()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }
       return ResponseEntity.ok(employeeMapper.employeeToEmployeeResponse(employee.get()));
    }

    @PostMapping("")
    // Dữ liệu request là EmployeeRequest, dữ liệu trả về là EmployeeResponse
    public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest employeeRequest) {
        // Chuyển EmployeeRequest về employee để lưu vào database
        Employee employee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        // Lưu
        employeeService.save(employee);
        // Trả về dạng EmployeeResponse
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeMapper.employeeToEmployeeResponse(employee));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable int id, @RequestBody EmployeeRequest employeeRequest) {
        // Kiểm tra nhân viên có tồn tại không
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Employee updatedEmployee = employeeMapper.employeeRequestToEmployee(employeeRequest);
        updatedEmployee.setId(id);
        employeeService.save(updatedEmployee);
        return ResponseEntity.ok(employeeMapper.employeeToEmployeeResponse(updatedEmployee));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> delete(@PathVariable int id) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        employeeService.delete(id);
        return ResponseEntity.ok(employeeMapper.employeeToEmployeeResponse(employee.get()));
    }

}
