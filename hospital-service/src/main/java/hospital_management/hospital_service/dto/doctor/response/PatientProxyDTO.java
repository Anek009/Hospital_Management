package hospital_management.hospital_service.dto.doctor.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientProxyDTO {
    private Long userId;
    private String name;
    private String imageUrl;
}
