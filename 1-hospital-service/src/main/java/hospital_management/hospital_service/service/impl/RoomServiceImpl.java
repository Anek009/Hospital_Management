package hospital_management.hospital_service.service.impl;

import hospital_management.hospital_service.dto.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Room;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.repository.RoomRepository;
import hospital_management.hospital_service.service.DepartmentService;
import hospital_management.hospital_service.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository roomRepository;
    private final DepartmentService departmentService;

    @Override
    public List<RoomResponseDTO> getAllRooms() {
        return roomRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public RoomResponseDTO getRoom(Integer roomId) {
        Room room = getRoomById(roomId);
        return mapToDTO(room);
    }

    public Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId).orElseThrow(() -> new CustomException(new Date(),
                "Room doesn't exist", HttpStatus.BAD_REQUEST));
    }

    private RoomResponseDTO mapToDTO(Room room) {
        return new RoomResponseDTO(
                room.getRoomNumber(),
                room.getDepartment().getDeptName(),
                room.isAvailable());
    }

    @Override
    public List<RoomResponseDTO> getAllRoomsByDepartmentName(String deptName) {
        return departmentService
                .getAllRoomsByDepartmentName(deptName)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void roomAssignToDoctor(Room room) {
        room.setAvailable(false);
        roomRepository.save(room);
    }

    @Override
    public void makeUnavailableRoomToAvailable(Integer roomId) {
        Room room = getRoomById(roomId);
        room.setAvailable(true);
        roomRepository.save(room);
    }
}