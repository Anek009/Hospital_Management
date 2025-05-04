package hospital_management.patient_service.service;

import hospital_management.patient_service.entity.MedicalRecord;
import hospital_management.patient_service.entity.Patient;
import hospital_management.patient_service.repository.MedicalRecordRepository;
import hospital_management.patient_service.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MedicalRecordService {
    @Autowired
    private final MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository, PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public Optional<MedicalRecord> addRecord(Long patientId, MedicalRecord record) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) return Optional.empty();

        record.setPatient(patientOpt.get());
        record.setRecordDate(LocalDate.now());
        return Optional.of(medicalRecordRepository.save(record));
    }

    public List<MedicalRecord> getRecordsByPatient(Long patientId) {
        return medicalRecordRepository.findByPatientId(patientId);
    }

    public Optional<MedicalRecord> uploadRecord(Long patientId, String diagnosis, String treatment, MultipartFile file) {
        Optional<Patient> patientOpt = patientRepository.findById(patientId);
        if (patientOpt.isEmpty()) return Optional.empty();

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path fileStorage = Paths.get("uploads").resolve(fileName);

        try {
            Files.createDirectories(fileStorage.getParent());
            Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            return Optional.empty();
        }

        MedicalRecord record = new MedicalRecord();
        record.setPatient(patientOpt.get());
        record.setDiagnosis(diagnosis);
        record.setTreatment(treatment);
        record.setRecordDate(LocalDate.now());
        record.setFilePath(fileStorage.toString());

        return Optional.of(medicalRecordRepository.save(record));
    }
}
