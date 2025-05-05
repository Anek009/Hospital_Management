package hospital_management.patient_service.service;

import hospital_management.patient_service.dto.request.LoginRequestDTO;
import hospital_management.patient_service.dto.request.RegisterRequestDTO;
import hospital_management.patient_service.dto.response.AuthenticationResponseDTO;
import hospital_management.patient_service.dto.response.LogInResponseDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO registerPatient(RegisterRequestDTO registerRequestDTO);
    public LogInResponseDTO loginPatient(LoginRequestDTO request);
    public Long getAuthenticatedUserId();

    public String getTokenFromPrincipal();

    public void handleAccessByUserRole(Long userId, String role);
}