package hospital_management.patient_service.repository;

import hospital_management.patient_service.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord,Long> {

    List<MedicalRecord> findByPatientId(Long patientId);
}
