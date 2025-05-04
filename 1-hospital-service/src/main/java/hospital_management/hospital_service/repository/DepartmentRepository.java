package hospital_management.hospital_service.repository;
import hospital_management.hospital_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    public Optional<Department> findByDeptName(String name);
}