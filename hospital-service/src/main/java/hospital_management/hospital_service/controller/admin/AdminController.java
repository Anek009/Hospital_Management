package hospital_management.hospital_service.controller.admin;

import hospital_management.hospital_service.dto.admin.response.PendingDoctorRegistrationResponseDTO;
import hospital_management.hospital_service.entity.Doctor;
import hospital_management.hospital_service.entity.enums.ApprovalStatus;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.repository.doctor.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
@RestController
@RequestMapping("/api/hms/admin")
@RequiredArgsConstructor
public class AdminController {

    private final DoctorRepository doctorRepository;

    @GetMapping("/pending-doctors")
    public ResponseEntity<List<Doctor>> getpendingDoctors(){
        var allByApprovalStatus = doctorRepository.findAllByApprovalStatus(ApprovalStatus.PENDING);
        return ResponseEntity.ok().body(allByApprovalStatus);
    }

    @PutMapping("/approve-doctor/{doctorId}")
    public ResponseEntity<String> approveDoctor(@PathVariable int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new CustomException(new Date(), "Doctor not found", HttpStatus.NOT_FOUND));

        if (doctor.getApprovalStatus() != ApprovalStatus.PENDING) {
            return ResponseEntity.badRequest().body("Doctor is already " + doctor.getApprovalStatus());
        }

        doctor.setApprovalStatus(ApprovalStatus.APPROVED);
        doctor.setAvailable(true);
        doctorRepository.save(doctor);

        return ResponseEntity.ok("Doctor approved successfully.");
    }

    @PutMapping("/reject-doctor/{doctorId}")
    public ResponseEntity<String> rejectDoctor(@PathVariable int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new CustomException(new Date(), "Doctor not found", HttpStatus.NOT_FOUND));

        if (doctor.getApprovalStatus() != ApprovalStatus.PENDING) {
            return ResponseEntity.badRequest().body("Doctor is already " + doctor.getApprovalStatus());
        }

        doctor.setApprovalStatus(ApprovalStatus.REJECTED);
        doctor.setAvailable(false);
        doctorRepository.save(doctor);

        return ResponseEntity.ok("Doctor rejected.");
    }
}

