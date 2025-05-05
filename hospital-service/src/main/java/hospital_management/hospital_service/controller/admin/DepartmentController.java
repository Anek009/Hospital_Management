package hospital_management.hospital_service.controller.admin;

import hospital_management.hospital_service.dto.admin.request.DepartmentRequestDTO;
import hospital_management.hospital_service.dto.admin.response.DepartmentResponseDTO;
import hospital_management.hospital_service.response.ResponseHandler;
import hospital_management.hospital_service.service.admin.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/hms/admin/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
//    /api/v1/admin/department/createDept
    @PostMapping("/createDept")
    public ResponseEntity<?> createDeptWithRooms(
            @Valid @RequestBody DepartmentRequestDTO departmentDTO) {
        departmentService.createDepartment(departmentDTO);
        return ResponseHandler.generateResponse(new Date(), "Department created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getAllDept")
    public ResponseEntity<Object> getAllDeptDetails() {
        List<DepartmentResponseDTO> department = departmentService.getAllDepartment();
        return ResponseHandler.generateResponse(new Date(), "All departments", HttpStatus.OK, department);
    }

    @DeleteMapping("/deleteDept/{departmentName}")
    public ResponseEntity<?> deleteDepartment(@Valid @PathVariable String departmentName) {
        departmentService.deleteDepartment(departmentName);
        return ResponseHandler.generateResponse(new Date(), "Delete Department Successfully", HttpStatus.OK);
    }
}