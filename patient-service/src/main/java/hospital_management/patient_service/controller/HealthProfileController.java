package hospital_management.patient_service.controller;

import hospital_management.patient_service.dto.request.HealthProfileDTO;
import hospital_management.patient_service.response.ResponseHandler;
import hospital_management.patient_service.service.AuthenticationService;
import hospital_management.patient_service.service.HealthProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient/health")
public class HealthProfileController {
    private final HealthProfileService healthProfileService;
    private final AuthenticationService authenticationService;

    @PostMapping("/createHealth")
    public ResponseEntity<?> createHealthProfile(@Valid @RequestBody HealthProfileDTO healthProfileDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        healthProfileService.createHealthProfile(healthProfileDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Successfully create profile", HttpStatus.OK);
    }

    @PutMapping("/updateHealth")
    public ResponseEntity<?> updateHealthProfile(@Valid @RequestBody HealthProfileDTO healthProfileDTO) {
        Long userId = authenticationService.getAuthenticatedUserId();
        healthProfileService.updateHealthProfile(healthProfileDTO, userId);
        return ResponseHandler.generateResponse(new Date(), "Successfully updated profile", HttpStatus.OK);
    }

    @GetMapping("/getByUserId/health-profile")
    public ResponseEntity<?> getHealthProfileByUserId() {
        Long userId = authenticationService.getAuthenticatedUserId();
        return ResponseHandler.generateResponse(new Date(), "Patient Data",
                HttpStatus.OK, healthProfileService.getUserHealthProfile(userId));
    }
}