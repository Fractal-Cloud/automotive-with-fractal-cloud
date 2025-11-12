package cloud.fractal.samples.automotive.application.writer.controller;

import cloud.fractal.samples.automotive.application.writer.entity.Employee;
import cloud.fractal.samples.automotive.application.writer.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Validated
public class EmployeeController {
  private final EmployeeService employeeService;

  @PostMapping("/")
  public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
    Employee savedEmployee = employeeService.saveEmployee(employee);
    return ResponseEntity.created(URI.create("/employees/" + savedEmployee.getId())).body(savedEmployee);
  }

  @PutMapping("/")
  public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
    return ResponseEntity.ok().body(employeeService.updateEmployee(employee));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") UUID id) {
    employeeService.deleteEmployeeById(id);
    return ResponseEntity.accepted().build();
  }
}
