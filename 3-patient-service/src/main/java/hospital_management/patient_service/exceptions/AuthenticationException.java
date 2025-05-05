package hospital_management.patient_service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationException extends RuntimeException {
    private HttpStatus status;
    private String message;
}