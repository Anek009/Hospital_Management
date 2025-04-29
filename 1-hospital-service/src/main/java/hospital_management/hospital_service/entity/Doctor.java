package hospital_management.hospital_service.entity;

import hospital_management.hospital_service.entity.enums.Degree;
import hospital_management.hospital_service.entity.enums.Designation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;

    private int deptId;
    private int roomId;

    private String name;
    private String image;

    private String departmentName;
    private String medicalName;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private String specialization;
    private int yearOfExperience;
    private LocalTime startTime;
    private LocalTime endTime;
    private int noOfDailyPatient;
    private boolean available = false;
}
