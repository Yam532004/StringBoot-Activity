package homework12._2.Am.homework12._2.repository.imp;

import homework12._2.Am.homework12._2.dto.employee.EmployeeSearchRequest;
import homework12._2.Am.homework12._2.emums.Gender;
import homework12._2.Am.homework12._2.modal.Employee;
import homework12._2.Am.homework12._2.repository.IEmployeeRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmployeeRepository implements IEmployeeRepository {
   List<Employee> employees = new ArrayList<>(
           Arrays.asList(
                   new Employee(UUID.randomUUID(), "Nguyen Van A", LocalDate.of(1990, 5, 20), Gender.MALE, 15000000.0, "0123456789", 1),
                   new Employee(UUID.randomUUID(), "Tran Thi B", LocalDate.of(1995, 8, 15), Gender.FEMALE, 12000000.0, "0987654321", 2),
                   new Employee(UUID.randomUUID(), "Le Van C", LocalDate.of(1988, 12, 10), Gender.MALE, 20000000.0, "0912345678", 3),
                   new Employee(UUID.randomUUID(), "Pham Thi D", LocalDate.of(2000, 1, 1), Gender.FEMALE, 10000000.0, "0908765432", 1),
                   new Employee(UUID.randomUUID(), "Hoàng", LocalDate.of(1990, 1, 1), Gender.MALE, 15000000.0, "0975123456", 2)
           )
   );

   @Override
   public List<Employee> findByAttributes(EmployeeSearchRequest employeeSearchRequest) {
       return employees.stream()
               // Tìm theo tên (không phân biệt hoa thường)
               .filter(e -> employeeSearchRequest.getName() == null ||
                       (e.getName() != null && e.getName().toLowerCase()
                               .contains(employeeSearchRequest.getName().toLowerCase())))
               // Tìm theo số điện thoại
               .filter(e -> employeeSearchRequest.getPhone() == null ||
                       (e.getPhone() != null && e.getPhone().equals(employeeSearchRequest.getPhone())))
               // Tìm theo mã bộ phận
               .filter(e -> employeeSearchRequest.getDepartmentId() == null ||
                       (e.getDepartmentId() != null && e.getDepartmentId().equals(employeeSearchRequest.getDepartmentId())))
               // Tìm theo giới tính
               .filter(e -> employeeSearchRequest.getGender() == null ||
                       (e.getGender() != null && e.getGender().equals(employeeSearchRequest.getGender())))
               // Tìm theo khoảng lương
               .filter(e -> employeeSearchRequest.getSalaryRange() == null ||
                       isSalaryInRange(e.getSalary(), String.valueOf(employeeSearchRequest.getSalaryRange())))
               // Tìm theo ngày sinh từ
               .filter(e -> employeeSearchRequest.getDobFrom() == null ||
                       (e.getDob() != null && !e.getDob().isBefore(employeeSearchRequest.getDobFrom())))
               // Tìm theo ngày sinh đến
               .filter(e -> employeeSearchRequest.getDobTo() == null ||
                       (e.getDob() != null && !e.getDob().isAfter(employeeSearchRequest.getDobTo())))
               .collect(Collectors.toUnmodifiableList());
   }

    // Hàm kiểm tra khoảng lương
    private boolean isSalaryInRange(Double salary, String salaryRange) {
        if (salary == null || !salaryRange.contains("-")) {
            return false;
        }
        String[] range = salaryRange.split("-");
        try {
            double minSalary = Double.parseDouble(range[0]);
            double maxSalary = Double.parseDouble(range[1]);
            return salary >= minSalary && salary <= maxSalary;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid salary range format. Expected format: min-max");
        }
    }


    @Override
    public Optional<Employee> findById(UUID id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Employee save(Employee employee) {
        return findById(employee.getId())
                .map(e->{
                    e.setName(employee.getName());
                    e.setDob(employee.getDob());
                    e.setGender(employee.getGender());
                    e.setSalary(employee.getSalary());
                    e.setPhone(employee.getPhone());
                    e.setDepartmentId(employee.getDepartmentId());
                    return e;
                })
                .orElseGet( ()-> {
                    employee.setId(UUID.randomUUID());
                    employees.add(employee);
                    return employee;
                });
    }

    @Override
    public void delete(UUID id) {
        findById(id).ifPresent(employees::remove);
    }
}
