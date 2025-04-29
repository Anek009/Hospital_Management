package hospital_management.auth_service.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record LoginRequest(
        @NonNull @Value(value = "email")
        String email,
        String password) {

}
