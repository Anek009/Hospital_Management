package hospital_management.hospital_service.entity;


import hospital_management.hospital_service.enums.ApprovalStatus;
import hospital_management.hospital_service.enums.Degree;
import hospital_management.hospital_service.enums.Designation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;

    private int deptId;
    private int roomId;
    private String email;
    private String name;
    private String image;
    private String departmentName;
    private String medicalName;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    @Enumerated(EnumType.STRING)
    private Designation designation;

    private String specialization;
    private float yearOfExperience;
    private LocalTime startTime;
    private LocalTime endTime;
    private int noOfDailyPatient;

    private boolean available = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    public Doctor(String name, String email, int room) {
        this.name = name;
        this.email=email;
        this.roomId= room;
    }
}