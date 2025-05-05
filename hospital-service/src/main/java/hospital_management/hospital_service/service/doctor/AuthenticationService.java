package hospital_management.hospital_service.service.doctor;

import hospital_management.hospital_service.dto.doctor.request.DoctorRegisterRequestDTO;
import hospital_management.hospital_service.dto.doctor.response.RegisterResponseDTO;

public interface AuthenticationService {
    public RegisterResponseDTO register(DoctorRegisterRequestDTO request);
    public Long getAuthenticatedUserId();
}