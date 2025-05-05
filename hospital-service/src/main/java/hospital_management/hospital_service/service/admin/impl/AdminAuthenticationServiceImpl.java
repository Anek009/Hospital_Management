package hospital_management.hospital_service.service.admin.impl;

import hospital_management.hospital_service.dto.AuthenticationResponseDTO;
import hospital_management.hospital_service.dto.LogInResponseDTO;
import hospital_management.hospital_service.dto.admin.request.AdminRequestDTO;
import hospital_management.hospital_service.dto.admin.request.RegisterRequestDTO;
import hospital_management.hospital_service.exception.FeignCustomException;
import hospital_management.hospital_service.feign.SecurityServiceClient;
import hospital_management.hospital_service.service.admin.AdminAuthenticationService;
import hospital_management.hospital_service.util.AppConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
@Slf4j
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

    @Autowired
    private SecurityServiceClient securityServiceClient;


    @Override
    public AuthenticationResponseDTO register(AdminRequestDTO request) {
        RegisterRequestDTO requestData = RegisterRequestDTO
                .builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AppConstant.ROLE_ADMIN)
                .build();

        try {
            return securityServiceClient.register(requestData);
        } catch (FeignCustomException ex) {
            log.error("Registration failed ", ex);
            throw ex;
        }
    }

}
