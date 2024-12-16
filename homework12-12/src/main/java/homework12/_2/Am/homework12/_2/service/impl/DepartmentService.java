package homework12._2.Am.homework12._2.service.impl;

import homework12._2.Am.homework12._2.service.IDepartmentService;
import homework12._2.Am.homework12._2.entity.Department;
import homework12._2.Am.homework12._2.repository.IDepartmentRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentService implements IDepartmentService {

    IDepartmentRepository departmentRepository;

    @Override
    public List<Department> findByName(String name) {
        return departmentRepository.findByName(name);
    }

    @Override
    public Optional<Department> findById(int id) {
        return departmentRepository.findById(id);
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void delete(int id) {
        departmentRepository.deleteById(id); // use deleteById method to delete by ID
    }
}
