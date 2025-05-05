package hospital_management.hospital_service.controller.admin;

import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;
import hospital_management.hospital_service.response.ResponseHandler;
import hospital_management.hospital_service.service.admin.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController

@RequestMapping("/api/hms/admin/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllRooms() {
        List<RoomResponseDTO> response = roomService.getAllRooms();
        return ResponseHandler.generateResponse(new Date(), "Fetch All Rooms Successfully",
                HttpStatus.OK, response);
    }

    @GetMapping("/getByDepartment/{deptName}")
    public ResponseEntity<?> getRoomsByDepartment(@PathVariable String deptName) {
        List<RoomResponseDTO> response = roomService.getAllRoomsByDepartmentName(deptName);
        return ResponseHandler.generateResponse(new Date(), "Fetch All Rooms Successfully",
                HttpStatus.OK, response);
    }
}