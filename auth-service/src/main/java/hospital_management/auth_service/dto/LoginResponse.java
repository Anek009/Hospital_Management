package hospital_management.auth_service.dto;

public record LoginResponse(String token, HttpStatus status) {
}
