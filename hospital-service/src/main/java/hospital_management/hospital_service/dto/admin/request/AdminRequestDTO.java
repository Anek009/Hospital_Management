package hospital_management.hospital_service.dto.admin.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequestDTO {
    @NotEmpty(message = "Email should not be null or empty")
    @Email(message = "Invalid email")
    private String email;

    @NotEmpty(message = "Password should not be null or empty")
    private String password;
}