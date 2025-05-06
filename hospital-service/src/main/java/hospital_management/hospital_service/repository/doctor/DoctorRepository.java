package hospital_management.hospital_service.repository.doctor;

import hospital_management.hospital_service.entity.Doctor;
import hospital_management.hospital_service.enums.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {
    public List<Doctor> findByDeptId(Integer deptId);

    public Optional<Doctor> findByUserId(Long userId);

    public Optional<Doctor> findByEmail(String email);
    List<Doctor> findAllByApprovalStatus(ApprovalStatus status);

}