package cloud.fractal.samples.automotive.application.reader.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Employee {
  @Id
  private UUID id;
  private String firstName;
  private String lastName;
  private String designation;
  private String phoneNumber;
  private LocalDate joinedOn;
  private String address;
  private LocalDate dateOfBirth;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
