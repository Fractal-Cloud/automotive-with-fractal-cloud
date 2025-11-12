package cloud.fractal.samples.automotive.application.writer.service;

import cloud.fractal.samples.automotive.application.writer.entity.Employee;
import cloud.fractal.samples.automotive.application.writer.exception.EmployeeNotFoundException;
import cloud.fractal.samples.automotive.application.writer.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  public Employee saveEmployee(Employee employee) {
    employee.setId();
    employee.setCreatedAtAndUpdatedAt();
    Employee savedEmployee = employeeRepository.save(employee);

    log.info("Employee with id: {} saved successfully", employee.getId());
    return savedEmployee;
  }

  public Employee updateEmployee(Employee employee) {
    Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());

    if (existingEmployee.isPresent()) {
      employee.setCreatedAt(existingEmployee.get().getCreatedAt());
      employee.setUpdatedAt(LocalDateTime.now());
      Employee updatedEmployee = employeeRepository.save(employee);

      log.info("Employee with id: {} updated successfully", employee.getId());
      return updatedEmployee;
    } else {
      throw new EmployeeNotFoundException(employee.getId());
    }
  }

  public void deleteEmployeeById(UUID id) {
    employeeRepository.deleteById(id);
  }

}
