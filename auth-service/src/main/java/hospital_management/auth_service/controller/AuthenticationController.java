package hospital_management.auth_service.controller;


import hospital_management.auth_service.dto.request.LogInRequestDTO;
import hospital_management.auth_service.dto.request.RegisterRequestDTO;
import hospital_management.auth_service.dto.response.AuthenticationResponseDTO;
import hospital_management.auth_service.response.ResponseHandler;
import hospital_management.auth_service.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LogInRequestDTO request) {
        return ResponseHandler.generateResponse(new Date(), "Login successful"
                , HttpStatus.OK, authenticationService.authenticate(request));
    }
}