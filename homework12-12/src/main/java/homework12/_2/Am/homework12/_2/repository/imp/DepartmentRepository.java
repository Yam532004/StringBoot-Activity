package homework12._2.Am.homework12._2.repository.imp;

import homework12._2.Am.homework12._2.modal.Department;
import homework12._2.Am.homework12._2.repository.IDepartmentRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DepartmentRepository implements IDepartmentRepository {
    List<Department> departments = new ArrayList<>(
            Arrays.asList(
                    new Department(1, "Quản lý"),
                    new Department(2, "Kế toán"),
                    new Department(3, "Sale Marketing"),
                    new Department(4, "Sản xuất")
            )

    );

    public List<Department> findByAttributes(Department department){
        return departments;
    }
    public Optional<Department> findById(int id){
        return departments.stream()
                .filter(d -> d.getId().equals(id))
                .findFirst();
    }
    public Department save(Department department){
        return findById(department.getId())
                .map( d ->{
                    d.setName(department.getName());
                    return d;
                })
                .orElseGet(() -> {
                    department.setId((int) (Math.random()*100000000));
                    departments.add(department);
                    return department;
                });
    }
    public void delete(int id){
        findById(id).ifPresent(departments::remove);
    }
}
