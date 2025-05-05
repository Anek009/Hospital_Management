package hospital_management.patient_service.controller;

import hospital_management.patient_service.dto.response.HealthProfileResponseDTO;
import hospital_management.patient_service.dto.response.PatientCountDTO;
import hospital_management.patient_service.dto.response.PatientProxyDTO;
import hospital_management.patient_service.dto.response.PatientResponseDTO;
import hospital_management.patient_service.service.HealthProfileService;
import hospital_management.patient_service.service.PatientService;
import hospital_management.patient_service.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient/proxy")
public class ProxyController {
    private final PatientService patientService;
    private final HealthProfileService healthProfileService;
    private final ProxyService proxyService;

    @GetMapping("/getByUserId/{userId}")
    public PatientResponseDTO getByUserId(@PathVariable Long userId) {
        return patientService.getPatientData(userId);
    }

    @GetMapping("/get_health-profile/user/{userId}")
    public HealthProfileResponseDTO getHealthProfileByUserId(@PathVariable Long userId) {
        return healthProfileService.getUserHealthProfile(userId);
    }

    @GetMapping("/getByUserIdList")
    public List<PatientProxyDTO> getByUserId(@RequestParam("patients") List<Long> patients) {
        return proxyService.getPatientListByUserId(patients);
    }

    @GetMapping("/count")
    public PatientCountDTO countPatient() {
        return patientService.countInfo();
    }
}
