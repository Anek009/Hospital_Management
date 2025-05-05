package hospital_management.patient_service.client;

import hospital_management.patient_service.dto.AppointmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "appointment-scheduler", url = "http://localhost:5000")  // Appointment Service URL
public interface AppointmentClient {

    @GetMapping("/api/appointments/{appointmentId}")
    AppointmentDTO getAppointmentById(@PathVariable("appointmentId") Long appointmentId);
}

