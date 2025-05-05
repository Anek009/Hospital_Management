package hospital_management.hospital_service.service.admin.impl;

import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Room;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.repository.admin.RoomRepository;
import hospital_management.hospital_service.service.admin.DepartmentService;
import hospital_management.hospital_service.service.admin.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    // Helper method to retrieve room by its ID
    private Room getRoomById(Integer roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(new Date(),
                        "Room doesn't exist", HttpStatus.BAD_REQUEST));
    }

    // Map Room entity to RoomResponseDTO
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
        room.setAvailable(false);  // Mark room as unavailable
        roomRepository.save(room);  // Save changes to database
    }

    @Override
    public void makeUnavailableRoomToAvailable(Integer roomId) {
        Room room = getRoomById(roomId);  // Retrieve the room by ID
        room.setAvailable(true);  // Mark room as available
        roomRepository.save(room);  // Save changes to database
    }

    // Method to find the first available room by department name

    public Optional<Room> findAvailableRoom(String departmentName) {
        return roomRepository.findFirstByDepartment_DeptNameAndAvailableTrue (departmentName);
    }

    // Method to fetch rooms by department ID
    @Override
    public void getRoomsByDepartmentId(int id) {
        List<Room> rooms = roomRepository.findByDepartment_Id(id);
        rooms.forEach(room -> System.out.println("Room: " + room.getRoomNumber()));  // Example action, could be mapping to DTO or further processing
    }
    @Override
    public void makeRoomAvailable(Integer roomId) {
        Room room = getRoomById(roomId);
        room.setAvailable(true);
        roomRepository.save(room);
    }
}
