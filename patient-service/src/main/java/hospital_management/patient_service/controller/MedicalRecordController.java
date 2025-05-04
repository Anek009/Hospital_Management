package hospital_management.patient_service.controller;

import hospital_management.patient_service.entity.MedicalRecord;
import hospital_management.patient_service.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class MedicalRecordController {
    @Autowired
    private final MedicalRecordService medicalRecordService;

    @Autowired
    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping("/patient/{patientId}")
    public ResponseEntity<MedicalRecord> addRecord(@PathVariable Long patientId, @RequestBody MedicalRecord record) {
        return medicalRecordService.addRecord(patientId, record)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientId}")
    public List<MedicalRecord> getRecordsByPatient(@PathVariable Long patientId) {
        return medicalRecordService.getRecordsByPatient(patientId);
    }

    @PostMapping(value = "/patient/{patientId}/upload", consumes = {"multipart/form-data"})
    public ResponseEntity<MedicalRecord> uploadRecord(
            @PathVariable Long patientId,
            @RequestParam("diagnosis") String diagnosis,
            @RequestParam("treatment") String treatment,
            @RequestParam("file") MultipartFile file) {

        return medicalRecordService.uploadRecord(patientId, diagnosis, treatment, file)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.internalServerError().build());
    }
}
