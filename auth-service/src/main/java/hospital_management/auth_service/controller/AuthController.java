package hospital_management.auth_service.controller;

import hospital_management.auth_service.dto.UserRegisterRequest;
import hospital_management.auth_service.entity.Role;
import hospital_management.auth_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
//  /api/auth/register/doctor
    @PostMapping("/register/doctor")
    public ResponseEntity<String> registerDoctor(@RequestBody UserRegisterRequest request) {
        String message = authService.registerUser(request, Role.DOCTOR);
        return ResponseEntity.ok(message);
    }
    @PostMapping("/register/patient")
    public ResponseEntity<String> registerPatient(@RequestBody UserRegisterRequest request) {
        String message = authService.registerUser(request, Role.PATIENT);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/register/staff")
    public ResponseEntity<String> registerStaff(@RequestBody UserRegisterRequest request) {
        String message = authService.registerUser(request, Role.STAFF);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody UserRegisterRequest request) {
        String message = authService.registerUser(request, Role.ADMIN);
        return ResponseEntity.ok(message);
    }
}
