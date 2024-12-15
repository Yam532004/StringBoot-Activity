package homework12._2.Am.homework12._2.repository;

import homework12._2.Am.homework12._2.modal.Department;
import homework12._2.Am.homework12._2.modal.Employee;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository {
    List<Department> findByAttributes(Department department);
    Optional<Department> findById(int id);
    Department save(Department department);
    void delete(int id);
}
