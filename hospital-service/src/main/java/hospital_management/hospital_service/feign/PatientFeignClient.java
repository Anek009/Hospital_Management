package hospital_management.hospital_service.feign;

import hospital_management.hospital_service.dto.admin.response.PatientCountDTO;
import hospital_management.hospital_service.dto.doctor.response.PatientProxyDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PATIENT-SERVICE", configuration = CustomFeignErrorDecoder.class)
public interface PatientFeignClient {
    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "countPatientFallback")
    @GetMapping("/api/patient/proxy/count")
    public PatientCountDTO countPatient();



    @CircuitBreaker(name = "CircuitBreakerService")
    @GetMapping("/api/v2/patient/proxy/getByUserIdList")
    public List<PatientProxyDTO> getByUserId(@RequestParam("patients") List<Long> patients);



    default public PatientCountDTO countPatientFallback(Throwable throwable) {
        return new PatientCountDTO(0,0);
    }
}