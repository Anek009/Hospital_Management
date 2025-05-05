package hospital_management.hospital_service.dto.admin.response;


import java.util.List;

public record DepartmentResponseDTO(
        Integer deptId,
        String deptName,
        int capacity,
        List<RoomResponseDTO> rooms
) {}
