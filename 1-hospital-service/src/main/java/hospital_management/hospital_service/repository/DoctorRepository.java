package hospital_management.hospital_service.repository;

import hospital_management.hospital_service.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
