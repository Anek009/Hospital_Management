package hospital_management.patient_service.controller;


import hospital_management.patient_service.dto.request.LoginRequestDTO;
import hospital_management.patient_service.dto.request.RegisterRequestDTO;
import hospital_management.patient_service.dto.response.AuthenticationResponseDTO;
import hospital_management.patient_service.response.ResponseHandler;
import hospital_management.patient_service.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequestDTO request) {
        AuthenticationResponseDTO responseDTO = authenticationService.registerPatient(request);
        return ResponseHandler.generateResponse(new Date(), "Registration Successful", HttpStatus.OK, responseDTO);
    }
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequestDTO request) {
        var responseDTO = authenticationService.loginPatient(request);
        System.out.println(responseDTO);
        return ResponseHandler.generateResponse(new Date(), "login Successful", HttpStatus.OK, responseDTO);
    }
}