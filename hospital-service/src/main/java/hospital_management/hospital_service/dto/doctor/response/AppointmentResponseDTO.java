package hospital_management.hospital_service.dto.doctor.response;


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
public class AppointmentResponseDTO {
    private Long patientId;
    private String name;
    private String imageUrl;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate appointmentDate;
    private String appointmentType;
}