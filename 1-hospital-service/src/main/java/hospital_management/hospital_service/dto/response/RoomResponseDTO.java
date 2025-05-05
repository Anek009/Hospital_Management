package hospital_management.hospital_service.dto.response;

public record RoomResponseDTO(
        int roomNumber,
        String deptName,
        boolean available
) {}
