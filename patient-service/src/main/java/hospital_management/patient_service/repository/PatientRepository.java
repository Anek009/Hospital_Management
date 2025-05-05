package hospital_management.patient_service.repository;

import hospital_management.patient_service.entity.Patient;
import hospital_management.patient_service.enums.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Integer>, JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByUserId(Long userId);

    public List<Patient> findByUserIdIn(List<Long> userIds);

    @Query("SELECT p.bloodGroup FROM Patient p WHERE p.userId = :userId")
    public BloodGroup findBloodGroupByUserId(Long userId);

    public long countByInterestedInBloodDonateTrue();
}
