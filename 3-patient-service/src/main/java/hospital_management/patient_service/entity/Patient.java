package hospital_management.patient_service.entity;



import hospital_management.patient_service.enums.BloodGroup;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long userId;

    @Column(nullable = false)
    private String name;
    private String imageUrl;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String mobileNo;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private BloodGroup bloodGroup;

    @Column(nullable = false)
    private Boolean interestedInBloodDonate;
}