package hospital_management.hospital_service.dto.admin.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponseDTO {
    private int roomNumber;
    private String deptName;
    private boolean available;
}