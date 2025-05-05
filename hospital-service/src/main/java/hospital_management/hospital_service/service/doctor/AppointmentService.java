package hospital_management.hospital_service.service.doctor;

import hospital_management.hospital_service.dto.doctor.request.AppointmentRequestDTO;
import hospital_management.hospital_service.dto.doctor.response.AppointmentDoctorDTO;
import hospital_management.hospital_service.dto.doctor.response.AppointmentResponseDTO;
import hospital_management.hospital_service.dto.doctor.response.AvailableSlotDTO;
import hospital_management.hospital_service.dto.doctor.response.DashboardResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentService {
    public void takeAppointment(AppointmentRequestDTO appointmentRequestDTO, Long patientId);

    public List<AppointmentResponseDTO> getAllAppointments(Long userId, LocalDate date);

    public List<AppointmentDoctorDTO> getPatientUpcomingAppointments(Long patientId);

    public List<AvailableSlotDTO> getAvailableSlots(Long userId, LocalDate date);

    public DashboardResponseDTO getDashboardData(long userId);
}