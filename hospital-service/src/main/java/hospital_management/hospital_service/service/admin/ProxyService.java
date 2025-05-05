package hospital_management.hospital_service.service.admin;

import hospital_management.hospital_service.dto.admin.response.RoomProxyResponseDTO;
import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;

public interface ProxyService {
    public RoomProxyResponseDTO assignRoomToDoctor(String departmentName);

    public RoomResponseDTO getRoomInfo(Integer roomId);

    public void makeRoomAvailable(Integer roomNo);
}