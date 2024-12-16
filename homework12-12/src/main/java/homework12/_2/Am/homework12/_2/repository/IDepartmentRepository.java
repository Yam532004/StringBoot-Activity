package homework12._2.Am.homework12._2.repository;

import homework12._2.Am.homework12._2.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {
    List<Department> findByAttributes(Department department);
    Optional<Department> findById(int id);
    Department save(Department department);
    void delete(int id);
}
