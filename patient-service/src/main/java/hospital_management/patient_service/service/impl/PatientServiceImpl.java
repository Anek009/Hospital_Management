package hospital_management.patient_service.service.impl;

import hospital_management.patient_service.dto.response.PatientCountDTO;
import hospital_management.patient_service.dto.response.PatientResponseDTO;
import hospital_management.patient_service.entity.Patient;
import hospital_management.patient_service.enums.BloodGroup;
import hospital_management.patient_service.exceptions.CustomException;
import hospital_management.patient_service.repository.PatientRepository;
import hospital_management.patient_service.repository.PatientSpecification;
import hospital_management.patient_service.service.AuthenticationService;
import hospital_management.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final AuthenticationService authenticationService;

    @Override
    public List<PatientResponseDTO> getAll() {
        log.info("Getting all patients");
        return patientRepository.findAll().stream().map(this::mapToPatientResponseDTO).toList();
    }

    @Override
    public PatientResponseDTO getByUserId(Long userId) {
        authenticationService.handleAccessByUserRole(userId, "ADMIN");
        log.info("Getting patient by user ID: {}", userId);
        return getPatientData(userId);
    }

    public PatientResponseDTO getPatientData(Long userId) {
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("Patient not found");
                    return new CustomException(new Date(), "Patient data", HttpStatus.NOT_FOUND);
                });
        return mapToPatientResponseDTO(patient);
    }

    @Override
    public List<PatientResponseDTO> search(String bloodGroup) {
        log.info("Searching patients by blood group: {}", bloodGroup);
        return patientRepository.findAll(PatientSpecification.dynamicQuery(bloodGroup))
                .stream().map(this::mapToPatientResponseDTO).toList();
    }

    @Override
    public String getBloodGroup(Long userId) {
        BloodGroup bloodGroup = patientRepository.findBloodGroupByUserId(userId);
        if (bloodGroup == null) {
            throw new CustomException(new Date(), "Failed to fetch blood group", HttpStatus.NOT_FOUND);
        }
        return bloodGroup.toString();
    }

    @Override
    public PatientCountDTO countInfo() {
        return PatientCountDTO
                .builder()
                .patientCount(patientRepository.count())
                .totalDonor(patientRepository.countByInterestedInBloodDonateTrue())
                .build();
    }

    private PatientResponseDTO mapToPatientResponseDTO(Patient patient) {
        return PatientResponseDTO
                .builder()
                .userId(patient.getUserId())
                .name(patient.getName())
                .imageUrl(patient.getImageUrl())
                .address(patient.getAddress())
                .mobileNo(patient.getMobileNo())
                .dateOfBirth(patient.getDateOfBirth())
                .bloodGroup(patient.getBloodGroup().toString())
                .interestedInBloodDonate(patient.getInterestedInBloodDonate())
                .build();
    }
}