package hospital_management.hospital_service.controller;

import hospital_management.hospital_service.dto.LogInRequestDTO;
import hospital_management.hospital_service.dto.admin.request.AdminRequestDTO;
import hospital_management.hospital_service.dto.AuthenticationResponseDTO;
import hospital_management.hospital_service.dto.doctor.request.DoctorRegisterRequestDTO;
import hospital_management.hospital_service.dto.doctor.response.RegisterResponseDTO;
import hospital_management.hospital_service.entity.Doctor;
import hospital_management.hospital_service.entity.enums.ApprovalStatus;
import hospital_management.hospital_service.exception.AuthenticationException;
import hospital_management.hospital_service.feign.SecurityServiceClient;
import hospital_management.hospital_service.repository.doctor.DoctorRepository;
import hospital_management.hospital_service.response.ResponseHandler;
import hospital_management.hospital_service.service.admin.AdminAuthenticationService;
import hospital_management.hospital_service.service.doctor.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/hms")
@AllArgsConstructor
public class AuthenticationController {

    private final AdminAuthenticationService adminService;
    private final AuthenticationService doctorService;
    private final SecurityServiceClient securityServiceClient;
    private final DoctorRepository doctorRepository;
//    /api/v1/register/admin
    @PostMapping("/register/admin")
    public ResponseEntity<Object> registerAdmin(@Valid @RequestBody AdminRequestDTO request) {
        AuthenticationResponseDTO responseDTO = adminService.register(request);
        return ResponseHandler.generateResponse(new Date(), "Registration Successful", HttpStatus.OK, responseDTO);
    }

    //    /api/v1/register/doctor
    @PostMapping("/register/doctor")
    public ResponseEntity<Object> register(@Valid @RequestBody DoctorRegisterRequestDTO request) {
        RegisterResponseDTO responseDTO = doctorService.register(request);
        return ResponseHandler.generateResponse(new Date(), "Approval pending, Admin needs to approve !", HttpStatus.OK, responseDTO);
    }

    @PostMapping("/login/admin")
    public ResponseEntity<Object> loginAdmin(@RequestBody LogInRequestDTO request){
        return ResponseHandler.generateResponse(new Date(), "Login successful"
                , HttpStatus.OK, securityServiceClient.authenticate(request));
    }
    @PostMapping("/login/doctor")
    public ResponseEntity<Object> loginDoctor(@RequestBody LogInRequestDTO request){

        Doctor doctor = doctorRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new AuthenticationException(HttpStatus.BAD_REQUEST,"try to register"));
        if(doctor.getApprovalStatus().equals(ApprovalStatus.APPROVED)){
            return ResponseHandler.generateResponse(new Date(), "Login successful"
                , HttpStatus.OK, securityServiceClient.authenticate(request));
        }
        return ResponseHandler.generateResponse(new Date(), "Login Failed"
                , HttpStatus.OK, "Login Approval status : "+ doctor.getApprovalStatus());
    }
}