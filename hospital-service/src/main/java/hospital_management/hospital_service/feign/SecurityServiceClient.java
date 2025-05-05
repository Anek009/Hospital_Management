package hospital_management.hospital_service.feign;


import hospital_management.hospital_service.dto.LogInRequestDTO;
import hospital_management.hospital_service.dto.admin.request.RegisterRequestDTO;
import hospital_management.hospital_service.dto.AuthenticationResponseDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "AUTH-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface SecurityServiceClient {
    @PostMapping("/api/auth/register")
    public AuthenticationResponseDTO register(@RequestBody RegisterRequestDTO request);


    @CircuitBreaker(name = "CircuitBreakerService" ,fallbackMethod = "authError")
    @PostMapping("api/auth/login")
    public ResponseEntity<?> authenticate(@RequestBody LogInRequestDTO request);

    default public String authError(Throwable throwable){
        return "unexpected Authentication Error..!";
    }
}
