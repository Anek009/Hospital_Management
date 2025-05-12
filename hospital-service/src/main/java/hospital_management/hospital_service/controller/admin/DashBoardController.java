package hospital_management.hospital_service.controller.admin;

import hospital_management.hospital_service.response.ResponseHandler;
import hospital_management.hospital_service.service.admin.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hms/admin/dashboard")
public class DashBoardController {
    private final DashBoardService dashBoardService;

    @GetMapping
    public ResponseEntity<?> getDashBoardInfo() {
        return ResponseHandler.generateResponse(new Date(), "Dashboard info",
                HttpStatus.OK, dashBoardService.getDashBoardInfo());
    }
}