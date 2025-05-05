package hospital_management.hospital_service.dto.admin.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record DepartmentRequestDTO(
        @NotEmpty(message = "Department name is required")
        String deptName,

        @NotNull(message = "Capacity is required")
        @Min(value = 0, message = "Capacity should not be negative")
        Integer capacity
) {}
