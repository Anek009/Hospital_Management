package hospital_management.hospital_service.service.admin;

import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Room;

import java.util.List;

public interface RoomService {
    // Fetch all rooms in the system
    List<RoomResponseDTO> getAllRooms();

    // Fetch a specific room by its ID
    RoomResponseDTO getRoom(Integer roomId);

    // Fetch all rooms in a specific department by department name
    List<RoomResponseDTO> getAllRoomsByDepartmentName(String deptName);

    // Assign a room to a doctor
    void roomAssignToDoctor(Room room);

    // Make a room unavailable (e.g., when it's being used)
    void makeUnavailableRoomToAvailable(Integer roomId);

    // Fetch rooms by department ID
    void getRoomsByDepartmentId(int id);

    // Make a room available again after being marked unavailable
    void makeRoomAvailable(Integer roomId);
}
