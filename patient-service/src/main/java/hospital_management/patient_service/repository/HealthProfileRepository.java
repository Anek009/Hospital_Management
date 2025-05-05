package hospital_management.patient_service.repository;

import hospital_management.patient_service.entity.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {
    public Optional<HealthProfile> findByUserId(Long userId);
}