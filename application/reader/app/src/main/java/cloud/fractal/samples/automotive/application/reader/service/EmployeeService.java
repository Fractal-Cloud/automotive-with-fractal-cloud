package cloud.fractal.samples.automotive.application.reader.service;

import cloud.fractal.samples.automotive.application.reader.entity.Employee;
import cloud.fractal.samples.automotive.application.reader.exception.EmployeeNotFoundException;
import cloud.fractal.samples.automotive.application.reader.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {
  private final EmployeeRepository employeeRepository;

  public List<Employee> getAllEmployees() {
    return employeeRepository.findAll();
  }

  public Employee getEmployeeById(UUID id) {
    return employeeRepository.findById(id)
      .orElseThrow(() -> new EmployeeNotFoundException(id));
  }
}
