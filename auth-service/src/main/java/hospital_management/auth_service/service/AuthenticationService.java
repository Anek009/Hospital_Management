package hospital_management.auth_service.service;


import hospital_management.auth_service.dto.request.LogInRequestDTO;
import hospital_management.auth_service.dto.request.RegisterRequestDTO;
import hospital_management.auth_service.dto.response.AuthenticationResponseDTO;
import hospital_management.auth_service.dto.response.LogInResponseDTO;

public interface AuthenticationService {
    public AuthenticationResponseDTO register(RegisterRequestDTO request);
    public LogInResponseDTO authenticate(LogInRequestDTO request);
}