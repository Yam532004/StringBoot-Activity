package homework12._2.Am.homework12._2.controller;

import homework12._2.Am.homework12._2.dto.ApiResponse;
import homework12._2.Am.homework12._2.dto.JsonResponse;
import homework12._2.Am.homework12._2.dto.exception.AppException;
import homework12._2.Am.homework12._2.dto.exception.ErrorCode;
import homework12._2.Am.homework12._2.model.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/department")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {
    private List<Department> departments = new ArrayList<>(
            Arrays.asList(
                    new Department(1, "Quản lý"),
                    new Department(2, "Kế toán"),
                    new Department(3, "Sale Marketing"),
                    new Department(4, "Sản xuất")
            )
    );

    @GetMapping
    public ResponseEntity getAll(){
        return JsonResponse.ok(
                departments
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getById (@PathVariable("id") int id ) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(JsonResponse::ok)
                .orElseThrow(()-> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> addDepart(@RequestBody Department department) {
        department.setId((int) (Math.random()*100000000));
        departments.add(department);
        return JsonResponse.created(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepart(@PathVariable("id") int id, @RequestBody Department department) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(d -> {
                    d.setName(department.getName());
                    return JsonResponse.ok(d);
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepart(@PathVariable("id") int id) {
        return departments.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .map(d -> {
                    departments.remove(d);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

}
