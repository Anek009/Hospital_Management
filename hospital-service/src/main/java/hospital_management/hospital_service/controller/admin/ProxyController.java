package hospital_management.hospital_service.controller.admin;

import hospital_management.hospital_service.dto.admin.request.RoomUnavailableRequestDTO;
import hospital_management.hospital_service.dto.admin.response.ProxyResponseDTO;
import hospital_management.hospital_service.dto.admin.response.RoomProxyResponseDTO;
import hospital_management.hospital_service.dto.admin.response.RoomResponseDTO;
import hospital_management.hospital_service.service.admin.DepartmentService;
import hospital_management.hospital_service.service.admin.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hms/admin/proxy")
public class ProxyController {
    private final ProxyService proxyService;
    private final DepartmentService departmentService;

    @PutMapping("/room/unavailable")
    public Mono<ProxyResponseDTO> makeRoomAvailable(@RequestBody RoomUnavailableRequestDTO room) {
        return Mono.fromRunnable(() -> proxyService.makeRoomAvailable(room.getRoomId()))
                .thenReturn(new ProxyResponseDTO("make room unavailable successful"));
    }

    @PutMapping("/assignDoctor/{departmentName}")
    public RoomProxyResponseDTO assignDoctorDeptAndRoom(@PathVariable String departmentName) {
        return proxyService.assignRoomToDoctor(departmentName);
    }

    @GetMapping("/roomInfo/{roomId}")
    public RoomResponseDTO getRoomInformation(@PathVariable Integer roomId) {
        return proxyService.getRoomInfo(roomId);
    }

    @GetMapping("/get/{departmentId}")
    public boolean departmentExist(@PathVariable int departmentId) {
        return departmentService.departmentIsExist(departmentId);
    }
}