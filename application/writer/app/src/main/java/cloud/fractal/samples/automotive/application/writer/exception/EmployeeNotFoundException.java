package cloud.fractal.samples.automotive.application.writer.exception;

import java.util.UUID;

public class EmployeeNotFoundException extends RuntimeException {

  public EmployeeNotFoundException(UUID id) {
    super("Could not find employee " + id);
  }
}