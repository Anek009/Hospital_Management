package hospital_management.hospital_service.service.doctor;


import hospital_management.hospital_service.dto.doctor.response.AllDoctorsDTO;
import hospital_management.hospital_service.dto.doctor.response.DoctorInfoDTO;
import hospital_management.hospital_service.entity.Doctor;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    public List<AllDoctorsDTO> getAllDoctors();

    public List<AllDoctorsDTO> getAllDoctorsByDeptId(int deptId);

    public DoctorInfoDTO getDoctorByUserId(Long userId);

    public List<AllDoctorsDTO> searchDoctors(String name, String department,
                                             String designation, Integer experience, String specialization);

    public void updateAvailability(Long doctorId, boolean available);

    public boolean isDoctorExist(Long userId);

    public Doctor getDoctor(Long doctorUserId);

    public long countDoctors();

    Optional<Doctor> findByEmail(String email);
}