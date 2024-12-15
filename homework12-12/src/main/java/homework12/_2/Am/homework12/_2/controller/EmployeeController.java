package homework12._2.Am.homework12._2.controller;


import homework12._2.Am.homework12._2.dto.Gender;
import homework12._2.Am.homework12._2.dto.JsonResponse;
import homework12._2.Am.homework12._2.dto.exception.AppException;
import homework12._2.Am.homework12._2.dto.exception.ErrorCode;
import homework12._2.Am.homework12._2.model.Employee;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private List<Employee> employees = new ArrayList<>(
            Arrays.asList(
                    new Employee(UUID.randomUUID(), "Nguyen Van A", LocalDate.of(1990, 5, 20), Gender.MALE, 15000000.0, "0123456789"),
                    new Employee(UUID.randomUUID(), "Tran Thi B", LocalDate.of(1995, 8, 15), Gender.FEMALE, 12000000.0, "0987654321"),
                    new Employee(UUID.randomUUID(), "Le Van C", LocalDate.of(1988, 12, 10), Gender.MALE, 20000000.0, "0912345678"),
                    new Employee(UUID.randomUUID(), "Pham Thi D", LocalDate.of(2000, 1, 1), Gender.FEMALE, 10000000.0, "0908765432")
            )
    );

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        return JsonResponse.ok(employees);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeByID(@PathVariable("id") UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        employee.setId(UUID.randomUUID());
        employees.add(employee);
        return JsonResponse.ok(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Employee employee) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(
                   e ->{
                       e.setName(employee.getName());
                       e.setDob(employee.getDob());
                       e.setGender(employee.getGender());
                       e.setSalary(employee.getSalary());
                       e.setPhone(employee.getPhone());
                       return JsonResponse.ok(e);
                   }
                )
                .orElseThrow(() -> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .map(e -> {
                    employees.remove(e); // Xóa nhân viên khỏi danh sách
                    return JsonResponse.noContent();// Trả về 204 No Content
                })
                .orElseThrow(()-> new AppException(ErrorCode.EMPLOYEE_NOT_EXIST));
    }
}
