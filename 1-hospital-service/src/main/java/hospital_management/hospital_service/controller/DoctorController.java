package hospital_management.hospital_service.controller;

import hospital_management.hospital_service.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hms/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // View all patients for a doctor
//    @GetMapping("/patients")
//    public List<?> getAllPatients() {
//        return doctorService.getAllPatients();
//    }

//    // Update a patient's medical status
//    @PutMapping("/patients/{patientId}")
//    public Patient updatePatientStatus(@PathVariable Long patientId, @RequestBody Patient patient) {
//        return doctorService.updatePatientStatus(patientId, patient);
//    }

    // Additional doctor functionalities...
}
