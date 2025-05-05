package hospital_management.hospital_service.dto.doctor.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterFeignDTO {
    private String email;
    private String password;
    private String role;
}