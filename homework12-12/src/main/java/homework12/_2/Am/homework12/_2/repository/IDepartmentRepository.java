package homework12._2.Am.homework12._2.repository;

import homework12._2.Am.homework12._2.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IDepartmentRepository extends JpaRepository<Department, Integer> {
    @Query("SELECT d FROM Department d WHERE :name IS NULL OR d.name = :name")
    List<Department>findByName(String name);
    Optional<Department> findById(int id);
    Department save(Department department);
    void deleteById(int id);
}
