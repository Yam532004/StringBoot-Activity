package homework12._2.Am.homework12._2.service;

import homework12._2.Am.homework12._2.entity.Department;

import java.util.List;
import java.util.Optional;

public interface IDepartmentService {
    List<Department> findByAttributes(Department department);
    Optional<Department> findById(int id);
    Department save(Department department);
    void delete(int id);
}
