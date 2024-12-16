package homework12._2.Am.homework12._2.controller;

import homework12._2.Am.homework12._2.repository.IDepartmentRepository;
import homework12._2.Am.homework12._2.service.IDepartmentService;
import homework12._2.Am.homework12._2.util.JsonResponse;
import homework12._2.Am.homework12._2.exception.AppException;
import homework12._2.Am.homework12._2.exception.ErrorCode;
import homework12._2.Am.homework12._2.entity.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/departments")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentController {

    IDepartmentService departmentService;

    @GetMapping
    public ResponseEntity getAll(){
        return JsonResponse.ok(
                departmentService
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity <?> getById (@PathVariable("id") int id ) {
        return departmentService.findById(id)
                .map(JsonResponse::ok)
                .orElseThrow(()-> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @PostMapping
    public ResponseEntity<?> addDepart(@RequestBody Department department) {
        department.setId((int) (Math.random()*100000000));
        departmentService.save(department);
        return JsonResponse.created(department);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepart(@PathVariable("id") int id, @RequestBody Department department) {
        return departmentService.findById(id)
                .map(d -> {
                    d.setName(department.getName());
                    return JsonResponse.ok(d);
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepart(@PathVariable("id") int id) {
        return departmentService.findById(id)
                .map(d -> {
                    departmentService.delete(id);
                    return JsonResponse.noContent();
                })
                .orElseThrow(() -> new AppException(ErrorCode.DEPARTMENT_NOT_EXIST));
    }

}
