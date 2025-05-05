package hospital_management.patient_service.service;


import hospital_management.patient_service.dto.request.HealthProfileDTO;
import hospital_management.patient_service.dto.response.HealthProfileResponseDTO;

public interface HealthProfileService {
    public void createHealthProfile(HealthProfileDTO healthProfileDTO, Long userId);

    public void updateHealthProfile(HealthProfileDTO healthProfileDTO, Long userId);

    public HealthProfileResponseDTO getUserHealthProfile(Long userId);
}