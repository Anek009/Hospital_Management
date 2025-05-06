package hospital_management.hospital_service.service.doctor.impl;


import hospital_management.hospital_service.dto.admin.request.RegisterRequestDTO;
import hospital_management.hospital_service.dto.admin.response.RoomProxyResponseDTO;
import hospital_management.hospital_service.dto.doctor.request.DoctorRegisterRequestDTO;
import hospital_management.hospital_service.dto.AuthenticationResponseDTO;
import hospital_management.hospital_service.dto.doctor.response.RegisterResponseDTO;
import hospital_management.hospital_service.entity.Doctor;
import hospital_management.hospital_service.enums.ApprovalStatus;
import hospital_management.hospital_service.enums.Degree;
import hospital_management.hospital_service.enums.Designation;
import hospital_management.hospital_service.exception.AuthenticationException;
import hospital_management.hospital_service.exception.CustomException;
import hospital_management.hospital_service.exception.FeignCustomException;
import hospital_management.hospital_service.feign.SecurityServiceClient;
import hospital_management.hospital_service.repository.doctor.DoctorRepository;
import hospital_management.hospital_service.service.admin.ProxyService;
import hospital_management.hospital_service.service.admin.RoomService;
import hospital_management.hospital_service.service.doctor.AuthenticationService;
import hospital_management.hospital_service.util.AppConstant;
import hospital_management.hospital_service.util.EnumValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ProxyService proxyService;
    private final SecurityServiceClient securityServiceClient;
    private final DoctorRepository doctorRepository;
    private final RoomService roomService;

    @Override
    public RegisterResponseDTO register(DoctorRegisterRequestDTO request) {
        log.info("Attempt to register doctor...");

        LocalTime endTime = calculateEndTime(request.getStartTime(), request.getNoOfDailyPatient());

        validateDegreeAndDesignation(request);

        // 1. Assign a room
        RoomProxyResponseDTO roomProxyResponseDTO = assignRoomForDoctor(request.getDepartmentName());

        // 2. Call Auth service for doctor registration
        AuthenticationResponseDTO authenticationResponseDTO = registerDoctor(request, roomProxyResponseDTO);

        // 3. Map to Doctor entity
        Doctor doctor = mapToDoctor(request, authenticationResponseDTO.getUserId(), roomProxyResponseDTO, endTime);

        // 4. Save doctor and handle rollback on failure
        try {
            doctorRepository.save(doctor);
        } catch (Exception e) {
            log.error("Doctor DB save failed. Performing rollback...", e);
            performRollBack(roomProxyResponseDTO.getRoomId());
            throw new CustomException(new Date(), "Doctor registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return mapToRegistrationResponseDTO(doctor, authenticationResponseDTO);
    }

    private RegisterResponseDTO mapToRegistrationResponseDTO(Doctor doctor,
                                                             AuthenticationResponseDTO authenticationResponseDTO) {
        return RegisterResponseDTO
                .builder()
                .userId(authenticationResponseDTO.getUserId())
                .role(authenticationResponseDTO.getRole())
                .token(authenticationResponseDTO.getToken())
                .roomId(doctor.getRoomId())
                .startTime(doctor.getStartTime())
                .endTime(doctor.getEndTime())
                .build();
    }

    private void validateDegreeAndDesignation(DoctorRegisterRequestDTO request) {
        log.info("Validating degree and designation...");
        EnumValidator.validateDesignation(request.getDesignation());
        EnumValidator.validateDegree(request.getDegree());
    }

    private RoomProxyResponseDTO assignRoomForDoctor(String departmentName) {
        try {
            return proxyService.assignRoomToDoctor(departmentName);
        } catch (FeignCustomException ex) {
            log.error("Failed to assign room for department: {}", departmentName, ex);
            throw ex;
        }
    }

    private AuthenticationResponseDTO registerDoctor(DoctorRegisterRequestDTO request,
                                                     RoomProxyResponseDTO roomProxyResponseDTO) {
        try {
            return securityServiceClient.register(mapToFeignRegisterDTO(request));
        } catch (FeignCustomException ex) {
            log.error("Failed to register doctor in auth service. Rolling back room...", ex);
            performRollBack(roomProxyResponseDTO.getRoomId());
            throw ex;
        }
    }

    private RegisterRequestDTO mapToFeignRegisterDTO(DoctorRegisterRequestDTO request) {
        return RegisterRequestDTO.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(AppConstant.ROLE_DOCTOR)
                .build();
    }

    private Doctor mapToDoctor(DoctorRegisterRequestDTO request, Long userId,
                               RoomProxyResponseDTO room, LocalTime endTime) {
        return Doctor.builder()
                .deptId(room.getDeptId())
                .userId(userId)
                .email(request.getEmail())
                .roomId(room.getRoomId())
                .name(request.getName())
                .image(request.getImage())
                .departmentName(request.getDepartmentName())
                .medicalName(request.getMedicalName())
                .degree(Degree.valueOf(request.getDegree()))
                .designation(Designation.valueOf(request.getDesignation()))
                .specialization(request.getSpecialization())
                .yearOfExperience(request.getYearOfExperience())
                .startTime(request.getStartTime())
                .endTime(endTime)
                .approvalStatus(ApprovalStatus.PENDING)
                .noOfDailyPatient(request.getNoOfDailyPatient())
                .available(false)
                .build();
    }

    private LocalTime calculateEndTime(LocalTime startTime, int noOfPatient) {
        long minutesLeft = Duration.between(startTime, LocalTime.of(23, 59)).toMinutes();
        long neededMinutes = noOfPatient * AppConstant.TIME_PER_PATIENT;
        if (neededMinutes > minutesLeft) {
            throw new CustomException(new Date(), "End time exceeds hospital working hours", HttpStatus.BAD_REQUEST);
        }
        return startTime.plusMinutes(neededMinutes);
    }

    private void performRollBack(int roomId) {
        try {
            roomService.makeRoomAvailable(roomId);
            log.info("Room rollback successful for roomId={}", roomId);
        } catch (Exception ex) {
            log.error("Room rollback failed for roomId={}", roomId, ex);
        }
    }

    @Override
    public Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return Long.parseLong(auth.getName());
        }
        throw new AuthenticationException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
}
