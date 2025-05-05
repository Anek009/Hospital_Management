package hospital_management.hospital_service.service.admin;

import hospital_management.hospital_service.dto.AuthenticationResponseDTO;
import hospital_management.hospital_service.dto.admin.request.AdminRequestDTO;

public interface AdminAuthenticationService {
    public AuthenticationResponseDTO register(AdminRequestDTO request);
}