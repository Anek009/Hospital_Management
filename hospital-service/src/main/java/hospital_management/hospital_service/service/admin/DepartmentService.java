package hospital_management.hospital_service.service.admin;


import hospital_management.hospital_service.dto.admin.request.DepartmentRequestDTO;
import hospital_management.hospital_service.dto.admin.response.DepartmentResponseDTO;
import hospital_management.hospital_service.entity.Room;

import java.util.List;

public interface DepartmentService {
    public void createDepartment(DepartmentRequestDTO departmentDTO);

    public List<Room> getAllRoomsByDepartmentName(String deptName);

    public List<DepartmentResponseDTO> getAllDepartment();

    public void deleteDepartment(String departmentName);

    public boolean departmentIsExist(int departmentId);
}