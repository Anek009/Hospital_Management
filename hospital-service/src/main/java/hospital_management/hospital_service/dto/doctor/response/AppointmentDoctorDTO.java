package hospital_management.hospital_service.dto.doctor.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDoctorDTO {
    private String doctorName;
    private String imageUrl;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appointmentDate;
    private String appointmentType;
}