package hospital_management.hospital_service.service;

import hospital_management.hospital_service.dto.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Room;

import java.util.List;

public interface RoomService {
    public List<RoomResponseDTO> getAllRooms();

    public RoomResponseDTO getRoom(Integer roomId);

    public List<RoomResponseDTO> getAllRoomsByDepartmentName(String deptName);

    public void roomAssignToDoctor(Room room);

    public void makeUnavailableRoomToAvailable(Integer roomId);
}