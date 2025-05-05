package hospital_management.patient_service.service;

import hospital_management.patient_service.dto.response.PatientCountDTO;
import hospital_management.patient_service.dto.response.PatientResponseDTO;

import java.util.List;

public interface PatientService {
    public List<PatientResponseDTO> getAll();

    public PatientResponseDTO getByUserId(Long userId);

    public PatientResponseDTO getPatientData(Long userId);

    public List<PatientResponseDTO> search(String bloodGroup);

    public String getBloodGroup(Long userid);

    public PatientCountDTO countInfo();
}
