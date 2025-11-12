package cloud.fractal.samples.automotive.application.writer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor
public class Employee {
  @Id
  private UUID id;
  @Setter
  private String firstName;
  @Setter
  private String lastName;
  @Setter
  private String designation;
  @Setter
  private String phoneNumber;
  @Setter
  private LocalDate joinedOn;
  @Setter
  private String address;
  @Setter
  private LocalDate dateOfBirth;
  @Setter
  private LocalDateTime createdAt;
  @Setter
  private LocalDateTime updatedAt;

  public void setId() {
    this.id = UUID.randomUUID();
  }

  public void setCreatedAtAndUpdatedAt() {
    var now = LocalDateTime.now();
    this.createdAt = now;
    this.updatedAt = now;
  }
}
