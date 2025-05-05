package hospital_management.hospital_service.dto.admin.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PendingDoctorRegistrationResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String departmentName;
    private String specialization;
    private String degree;
    private String designation;
    private boolean approved;
    private float yearOfExperience;

}
