package hospital_management.hospital_service.controller.doctor;

import hospital_management.hospital_service.dto.doctor.response.AllDoctorsDTO;
import hospital_management.hospital_service.dto.doctor.response.DoctorInfoDTO;
import hospital_management.hospital_service.entity.Doctor;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.response.ResponseHandler;
import hospital_management.hospital_service.service.doctor.AuthenticationService;
import hospital_management.hospital_service.service.doctor.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hms/doctor")
public class DoctorController {
    private final DoctorService doctorService;
    private final AuthenticationService authenticationService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllDoctors() {
        List<AllDoctorsDTO> response = doctorService.getAllDoctors();
        return ResponseHandler.generateResponse(new Date(), "Fetch All Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllDoctorsList() {
        var response = doctorService.getAllDoctorsList();
        return ResponseHandler.generateResponse(new Date(), "Fetch All Data Successfully", HttpStatus.OK, response);
    }

    @PostMapping("/status/{email}")
    public ResponseEntity<?> getDoctorStatus(@PathVariable String email  ) {
        Doctor doctor = doctorService.findByEmail(email).orElseThrow(()->new CustomException(new Date(), "registration not started", HttpStatus.NO_CONTENT));
        return ResponseHandler.generateResponse(new Date(),"Registration status : " + doctor.getApprovalStatus(),  HttpStatus.OK, doctor);
    }

    @GetMapping("/department/{deptId}")
    public ResponseEntity<?> getAllDoctorsByDeptId(@PathVariable int deptId) {
        List<AllDoctorsDTO> response = doctorService.getAllDoctorsByDeptId(deptId);
        return ResponseHandler.generateResponse(new Date(), "Fetch All Data Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<?> getDoctorByUserId(@PathVariable Long doctorId) {
        DoctorInfoDTO response = doctorService.getDoctorByUserId(doctorId);
        return ResponseHandler.generateResponse(new Date(), "Fetch Doctor Data Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/available/{available}")
    public ResponseEntity<?> updateAvailable(@PathVariable boolean available) {
        Long doctorId = authenticationService.getAuthenticatedUserId();
        doctorService.updateAvailability(doctorId, available);
        return ResponseHandler.generateResponse(new Date(), "Updated available status", HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchDoctors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String designation,
            @RequestParam(required = false) Integer experience,
            @RequestParam(required = false) String specialization) {
        return ResponseHandler.generateResponse(new Date(), "Doctors data based on searching parameter"
                , HttpStatus.OK, doctorService.searchDoctors(name, department, designation, experience, specialization));
    }
}