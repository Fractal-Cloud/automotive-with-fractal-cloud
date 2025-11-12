package cloud.fractal.samples.automotive.application.reader.controller;

import cloud.fractal.samples.automotive.application.reader.entity.Employee;
import cloud.fractal.samples.automotive.application.reader.exception.EmployeeNotFoundException;
import cloud.fractal.samples.automotive.application.reader.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {
  private final EmployeeService employeeService;

  @GetMapping("/")
  public ResponseEntity<List<Employee>> getAllEmployees() {
    return ResponseEntity.ok().body(employeeService.getAllEmployees());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") UUID id) {
    try {
      return ResponseEntity.ok().body(employeeService.getEmployeeById(id));
    } catch (EmployeeNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
