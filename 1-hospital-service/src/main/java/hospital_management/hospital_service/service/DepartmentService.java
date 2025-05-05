package hospital_management.hospital_service.service;


import hospital_management.hospital_service.dto.request.DepartmentRequestDTO;
import hospital_management.hospital_service.dto.response.DepartmentResponseDTO;
import hospital_management.hospital_service.entity.Room;

import java.util.List;

public interface DepartmentService {
    public void createDepartment(DepartmentRequestDTO departmentDTO);

    public List<Room> getAllRoomsByDepartmentName(String deptName);

    public List<DepartmentResponseDTO> getAllDepartment();

    public void deleteDepartment(String departmentName);

    public boolean departmentIsExist(int departmentId);
}