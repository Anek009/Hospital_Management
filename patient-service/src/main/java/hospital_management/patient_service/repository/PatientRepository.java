package hospital_management.patient_service.repository;

import hospital_management.patient_service.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
