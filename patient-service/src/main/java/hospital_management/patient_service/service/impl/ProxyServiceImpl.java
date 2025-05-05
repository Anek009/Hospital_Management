package hospital_management.patient_service.service.impl;

import hospital_management.patient_service.dto.response.PatientProxyDTO;
import hospital_management.patient_service.entity.Patient;
import hospital_management.patient_service.repository.PatientRepository;
import hospital_management.patient_service.service.ProxyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {
    private final PatientRepository patientRepository;

    @Override
    public List<PatientProxyDTO> getPatientListByUserId(List<Long> userIdList) {
        return patientRepository
                .findByUserIdIn(userIdList)
                .stream()
                .map(this::mapToDTO)
                .sorted(Comparator.comparing(PatientProxyDTO::getUserId))
                .toList();
    }

    private PatientProxyDTO mapToDTO(Patient patient) {
        return PatientProxyDTO
                .builder()
                .userId(patient.getUserId())
                .name(patient.getName())
                .imageUrl(patient.getImageUrl())
                .build();
    }

}