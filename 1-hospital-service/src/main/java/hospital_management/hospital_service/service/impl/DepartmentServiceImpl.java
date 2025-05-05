package hospital_management.hospital_service.service.impl;

import hospital_management.hospital_service.dto.request.DepartmentRequestDTO;
import hospital_management.hospital_service.dto.response.DepartmentResponseDTO;
import hospital_management.hospital_service.dto.response.RoomResponseDTO;
import hospital_management.hospital_service.entity.Department;
import hospital_management.hospital_service.entity.Room;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.repository.DepartmentRepository;
import hospital_management.hospital_service.repository.RoomRepository;
import hospital_management.hospital_service.service.DepartmentService;
import hospital_management.hospital_service.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final RoomRepository roomRepository;
    @Override
    public void createDepartment(DepartmentRequestDTO departmentDTO) {
        checkDepartmentIfExist(departmentDTO.deptName());
        validateDepartmentData(departmentDTO.capacity());

        int startingRoomNumber = calculateStartingRoomNumber();
        Department department = mapToDepartment(departmentDTO);

        departmentRepository.save(department);
        createAndAssignRooms(department, startingRoomNumber, departmentDTO.capacity());
    }

    public int calculateStartingRoomNumber() {
        Integer lastRoom = roomRepository.getLastRoomNo();
        return lastRoom != null ? (lastRoom / 1000 + 1) * 1000 : AppConstant.STARTING_ROOM_NUMBER;
    }

    @Override
    public List<Room> getAllRoomsByDepartmentName(String deptName) {
        return departmentRepository.findByDeptName(deptName).map(Department::getRooms)
                .orElseThrow(() -> new CustomException(new Date(), "Department doesn't exist", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<DepartmentResponseDTO> getAllDepartment() {
        return departmentRepository.findAll()
                .stream()
                .map(this::mapToDepartmentResponse)
                .toList();
    }

    private DepartmentResponseDTO mapToDepartmentResponse(Department department) {
        List<RoomResponseDTO> roomResponseDTOS = department
                .getRooms()
                .stream()
                .map(this::mapToRoomDTO)
                .toList();

        return new DepartmentResponseDTO(
                department.getId(),
                department.getDeptName(),
                department.getCapacity(),
                roomResponseDTOS);
    }

    private RoomResponseDTO mapToRoomDTO(Room room) {
        return new RoomResponseDTO(
                room.getRoomNumber(),
                room.getDepartment().getDeptName(),
                room.isAvailable());

    }

    private Department mapToDepartment(DepartmentRequestDTO departmentDTO) {
        return Department
                .builder()
                .deptName(departmentDTO.deptName())
                .capacity(departmentDTO.capacity())
                .build();
    }

    private void checkDepartmentIfExist(String deptName) {
        departmentRepository.findByDeptName(deptName).ifPresent(department -> {
            throw new CustomException(new Date(), "Department " + deptName + " already exists",
                    HttpStatus.BAD_REQUEST);
        });
    }

    private void createAndAssignRooms(Department department, int startingRoomNumber, int deptCapacity) {
        List<Room> rooms = new ArrayList<>();

        for (int i = 0; i < deptCapacity; i++) {
            Room room = new Room();
            room.setRoomNumber(startingRoomNumber + i);
            room.setDepartment(department);
            room.setAvailable(true);
            rooms.add(room);
        }
        roomRepository.saveAll(rooms);
    }

    private void validateDepartmentData(int capacity) {
        if (capacity > AppConstant.MAX_ROOMS_PER_DEPARTMENT || capacity < 1) {
            throw new CustomException(new Date(),
                    "Room capacity must be less then or equal 1000 and more then 0", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteDepartment(String departmentName) {
        Department department = departmentRepository.findByDeptName(departmentName).orElseThrow(() ->
                new CustomException(new Date(), "Department " + departmentName + " doesn't exist exists",
                        HttpStatus.BAD_REQUEST));
        departmentRepository.delete(department);
    }

    @Override
    public boolean departmentIsExist(int departmentId) {
        return departmentRepository.existsById(departmentId);
    }
}