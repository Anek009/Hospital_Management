package hospital_management.patient_service.service;

import hospital_management.patient_service.dto.response.PatientProxyDTO;

import java.util.List;

public interface ProxyService {
    public List<PatientProxyDTO> getPatientListByUserId(List<Long> userIdList);
}