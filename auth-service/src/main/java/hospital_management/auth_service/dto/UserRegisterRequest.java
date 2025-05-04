package hospital_management.auth_service.dto;

public record UserRegisterRequest(
        String fullName,
        String email,
        String password,
        String contactNumber) {}

