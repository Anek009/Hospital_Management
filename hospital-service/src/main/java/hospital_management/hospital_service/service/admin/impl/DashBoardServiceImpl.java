package hospital_management.hospital_service.service.admin.impl;

import hospital_management.hospital_service.dto.admin.response.DashBoardDTO;
import hospital_management.hospital_service.dto.admin.response.PatientCountDTO;
import hospital_management.hospital_service.feign.PatientFeignClient;
import hospital_management.hospital_service.repository.admin.DepartmentRepository;
import hospital_management.hospital_service.service.admin.DashBoardService;
import hospital_management.hospital_service.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final PatientFeignClient patientFeignClient;
    private final DepartmentRepository departmentRepository;
    private final DoctorService doctorService;



    @Override
    public DashBoardDTO getDashBoardInfo() {
        PatientCountDTO patientCountDTO = patientFeignClient.countPatient();
        long totalDoctor=doctorService.countDoctors();
        return DashBoardDTO
                .builder()
                .patientCount(patientCountDTO.getPatientCount())
                .totalDonor(patientCountDTO.getTotalDonor())
                .totalDoctor(totalDoctor)
                .totalDepartment(departmentRepository.count())
                .build();
//        return new DashBoardDTO(1,1,1,3);
    }
}
