package hospital_management.auth_service.service;

import hospital_management.auth_service.dto.UserRegisterRequest;
import hospital_management.auth_service.entity.Role;
import hospital_management.auth_service.entity.User;
import hospital_management.auth_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(UserRegisterRequest request, Role userRole) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setContactNumber(request.contactNumber());

        user.setRole(userRole);
        if(userRole == Role.PATIENT) user.setStatus("APPROVED");
        user.setStatus("PENDING");
        userRepository.save(user);

        // optionally: send notification / publish event
        return userRole == Role.PATIENT ? " registered successfully.":  userRole + " registered successfully. Awaiting approval.";
    }
}

