package hospital_management.hospital_service.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long patientUserId;
    private Long doctorUserId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    private hospital_management.hospital_service.entity.enums.AppointmentType appointmentType;
}