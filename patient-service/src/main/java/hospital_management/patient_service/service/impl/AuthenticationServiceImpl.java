package hospital_management.patient_service.service.impl;

import hospital_management.patient_service.dto.request.LoginRequestDTO;
import hospital_management.patient_service.dto.request.RegisterFeignDTO;
import hospital_management.patient_service.dto.request.RegisterRequestDTO;
import hospital_management.patient_service.dto.response.AuthenticationResponseDTO;
import hospital_management.patient_service.dto.response.LogInResponseDTO;
import hospital_management.patient_service.entity.Patient;
import hospital_management.patient_service.enums.BloodGroup;
import hospital_management.patient_service.exceptions.AuthenticationException;
import hospital_management.patient_service.exceptions.CustomException;
import hospital_management.patient_service.exceptions.FeignCustomException;
import hospital_management.patient_service.feign.SecurityServiceClient;
import hospital_management.patient_service.repository.PatientRepository;
import hospital_management.patient_service.security.CustomWebAuthentication;
import hospital_management.patient_service.service.AuthenticationService;
import hospital_management.patient_service.utils.AppConstant;
import hospital_management.patient_service.utils.EnumValidators;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PatientRepository patientRepository;
    private final SecurityServiceClient securityServiceClient;

    @Override
    public AuthenticationResponseDTO registerPatient(RegisterRequestDTO registerRequestDTO) {
        validateData(registerRequestDTO);

        AuthenticationResponseDTO authenticationResponseDTO = register(registerRequestDTO);
        Patient patient = mapToPatient(registerRequestDTO, authenticationResponseDTO);
        patientRepository.save(patient);
        return authenticationResponseDTO;
    }

    @Override
    public LogInResponseDTO loginPatient(LoginRequestDTO request) {
        try {
            log.debug(" patient login  .........");
            return securityServiceClient.authenticate(request);
        } catch (FeignCustomException ex) {
            log.error("Login failed ", ex);
            throw ex;
        }
    }

    private void validateData(RegisterRequestDTO registerRequestDTO) {
        EnumValidators.parseBloodGroup(registerRequestDTO.getBloodGroup());
        if (!registerRequestDTO.getDateOfBirth().isBefore(LocalDate.now())) {
            throw new CustomException(new Date(), "Birth date must be before today", HttpStatus.BAD_REQUEST);
        }
    }

    private Patient mapToPatient(RegisterRequestDTO registerRequestDTO,
                                 AuthenticationResponseDTO authenticationResponseDTO) {
        boolean isInterested = registerRequestDTO.getInterestedInBloodDonate() != null ?
                registerRequestDTO.getInterestedInBloodDonate() : false;
        return Patient
                .builder()
                .userId(authenticationResponseDTO.getUserId())
                .name(registerRequestDTO.getName())
                .imageUrl(registerRequestDTO.getImageUrl())
                .address(registerRequestDTO.getAddress())
                .mobileNo(registerRequestDTO.getMobileNo())
                .dateOfBirth(registerRequestDTO.getDateOfBirth())
                .bloodGroup(BloodGroup.valueOf(registerRequestDTO.getBloodGroup()))
                .interestedInBloodDonate(isInterested)
                .build();
    }

    private RegisterFeignDTO mapToFeignDTO(RegisterRequestDTO registerRequestDTO) {
        return RegisterFeignDTO
                .builder()
                .email(registerRequestDTO.getEmail())
                .password(registerRequestDTO.getPassword())
                .role(AppConstant.ROLE_PATIENT)
                .build();
    }

    private AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        RegisterFeignDTO registerFeignDTO = mapToFeignDTO(registerRequestDTO);
        try {
            log.debug("Registering patient .........");
            return securityServiceClient.register(registerFeignDTO);
        } catch (FeignCustomException ex) {
            log.error("Registration failed ", ex);
            throw ex;
        }
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return Long.parseLong(authentication.getName());
        } else {
            throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
        }
    }

    @Override
    public String getTokenFromPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            CustomWebAuthentication details = (CustomWebAuthentication) authentication.getDetails();
            return "Bearer " + details.getJwtToken();
        }
        return null;
    }

    @Override
    public void handleAccessByUserRole(Long userId, String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Long idFromToken = Long.parseLong(authentication.getName());
            if (!authentication.getAuthorities()
                    .contains(new SimpleGrantedAuthority("ROLE_" + role)) && userId != idFromToken) {
                throw new CustomException(new Date(), "You don't have access to another patient's health information"
                        , HttpStatus.BAD_REQUEST);
            }
        }
    }
}