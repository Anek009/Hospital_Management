package hospital_management.hospital_service.dto.admin.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomProxyResponseDTO {
    private int roomId;
    private int deptId;
}