package hospital_management.patient_service.controller;


import hospital_management.patient_service.response.ResponseHandler;
import hospital_management.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient/profile")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/get/all")
    public ResponseEntity<?> getAll() {
        return ResponseHandler.generateResponse(new Date(), "Patient List",
                HttpStatus.OK, patientService.getAll());
    }

    @GetMapping("/getByUserId/{userId}")
    public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
        return ResponseHandler.generateResponse(new Date(), "Patient Information",
                HttpStatus.OK, patientService.getByUserId(userId));
    }

    //search users who is interested in blood donate
    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(required = false) String bloodGroup) {
        return ResponseHandler.generateResponse(new Date(), "Patient List",
                HttpStatus.OK, patientService.search(bloodGroup));
    }
}
