package hospital_management.hospital_service.service.admin.impl;

import hospital_management.hospital_service.dto.admin.response.RoomProxyResponseDTO;
import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Room;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.service.admin.DepartmentService;
import hospital_management.hospital_service.service.admin.ProxyService;
import hospital_management.hospital_service.service.admin.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProxyServiceImpl implements ProxyService {
    private final DepartmentService departmentService;
    private final RoomService roomService;

    //Assigns the first available room in a specified department to a doctor
    @Override
    public RoomProxyResponseDTO assignRoomToDoctor(String departmentName) {
        log.info("Assigning room in department: {}", departmentName);
        List<Room> roomList = departmentService.getAllRoomsByDepartmentName(departmentName);
        Optional<Room> firstAvailableRoom = roomList.stream()
                .filter(Room::isAvailable)
                .findFirst();

        if (firstAvailableRoom.isEmpty()) {
            log.error("No available room found in department: {}", departmentName);
            throw new CustomException(new Date(), "Room not available", HttpStatus.BAD_REQUEST);
        }

        Room availableRoom = firstAvailableRoom.get();
        roomService.roomAssignToDoctor(availableRoom);
        log.info("Room assigned: Room ID {} in Department ID {}",
                availableRoom.getId(), availableRoom.getDepartment().getId());

        return RoomProxyResponseDTO.builder()
                .roomId(availableRoom.getId())
                .deptId(availableRoom.getDepartment().getId())
                .build();
    }

    @Override
    public RoomResponseDTO getRoomInfo(Integer roomId) {
        return roomService.getRoom(roomId);
    }

    @Override
    public void makeRoomAvailable(Integer roomId) {
        roomService.makeUnavailableRoomToAvailable(roomId);
    }
}