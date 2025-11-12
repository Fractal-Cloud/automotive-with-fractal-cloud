package cloud.fractal.samples.automotive.application.writer.repository;

import cloud.fractal.samples.automotive.application.writer.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
