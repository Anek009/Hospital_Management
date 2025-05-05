package hospital_management.auth_service.service.impl;

import hospital_management.auth_service.dto.request.LogInRequestDTO;
import hospital_management.auth_service.dto.request.RegisterRequestDTO;
import hospital_management.auth_service.dto.response.AuthenticationResponseDTO;
import hospital_management.auth_service.dto.response.LogInResponseDTO;
import hospital_management.auth_service.entity.Role;
import hospital_management.auth_service.entity.User;
import hospital_management.auth_service.exception.AuthenticationException;
import hospital_management.auth_service.jwt.JwtService;
import hospital_management.auth_service.repository.UserRepository;
import hospital_management.auth_service.service.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Email address is already taken.");
        }

        Role userRole;
        if ("PATIENT".equalsIgnoreCase(request.getRole())) {
            userRole = Role.PATIENT;
        } else if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            userRole = Role.ADMIN;
        } else if ("DOCTOR".equalsIgnoreCase(request.getRole())) {
            userRole = Role.DOCTOR;
        } else {
            throw new AuthenticationException(HttpStatus.BAD_REQUEST, "Invalid role. Supported roles are USER and ADMIN.");
        }

        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        User newUser = userRepository.save(user);
        //var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponseDTO.builder()
                .userId(newUser.getId())
                .email(user.getEmail())
                .role(user.getRole().toString())
//                .token(jwtToken)
                .build();
    }

    @Override
    public LogInResponseDTO authenticate(LogInRequestDTO request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new AuthenticationException(HttpStatus.NOT_FOUND, "User not found"));

            var jwtToken = jwtService.generateToken(user);
            return LogInResponseDTO.builder()
                    .userId(user.getId())
                    .token(jwtToken)
                    .role(user.getRole().toString())
                    .build();
        } catch (BadCredentialsException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "Invalid email or password");
        } catch (ExpiredJwtException e) {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "Session has expired. Please log in again");
        } catch (Exception e) {
            throw new AuthenticationException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed");
        }
    }
}
