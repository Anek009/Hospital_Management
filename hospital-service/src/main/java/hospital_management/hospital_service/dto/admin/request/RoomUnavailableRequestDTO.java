package hospital_management.hospital_service.dto.admin.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomUnavailableRequestDTO {
    private Integer roomId;
}