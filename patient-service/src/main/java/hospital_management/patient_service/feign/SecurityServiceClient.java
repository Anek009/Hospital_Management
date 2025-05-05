package hospital_management.patient_service.feign;

import hospital_management.patient_service.dto.request.LoginRequestDTO;
import hospital_management.patient_service.dto.request.RegisterFeignDTO;
import hospital_management.patient_service.dto.response.AuthenticationResponseDTO;
import hospital_management.patient_service.dto.response.LogInResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTH-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface SecurityServiceClient {
    @PostMapping("/api/auth/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterFeignDTO request);

    @PostMapping("/api/auth/login")
    public LogInResponseDTO authenticate(@RequestBody LoginRequestDTO request);
}