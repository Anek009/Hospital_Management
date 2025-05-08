package hospital_management.hospital_service.util;

public class AppConstant {
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_PATIENT = "PATIENT";
    public static final String ROLE_DOCTOR = "DOCTOR";
    public static final Integer MAX_ROOMS_PER_DEPARTMENT = 1000;
    public static final Integer STARTING_ROOM_NUMBER = 1000;

    //DOCTOR

    public static final long TIME_PER_PATIENT = 15;
    public static final long MAX_RETRY_ATTEMPTS = 10;
    public static final long RETRY_TIME_GAP = 20;

    public static final String DOCTOR_GET_ALL = "/api/hms/doctor/getAll";
    public static final String DOCTOR_DEPARTMENT = "/api/hms/doctor/department/{deptId}";
    public static final String DOCTOR_BY_ID = "/api/hms/doctor/{doctorId}";
    public static final String DOCTOR_AVAILABLE = "/api/hms/doctor/available/{available}";
    public static final String DOCTOR_SEARCH = "/api/hms/doctor/search";
    public static final String DOCTOR_APPOINTMENT_TAKE = "/api/hms/doctor/appointment/take";
    public static final String DOCTOR_APPOINTMENT_GET = "/api/hms/appointment/get";
    public static final String PATIENT_UPCOMING_APPOINTMENTS = "/api/hms/appointment/get/patient/upcoming";
    public static final String DOCTOR_FREE_SLOT = "/api/hms/appointment/doctor/{userId}/freeSlot/{date}";
    public static final String DOCTOR_DASHBOARD = "/api/hms/appointment/dashboard";
    public static final String PROXY_API = "/api/proxy/**";
    public static final String ACTUATOR_API = "/actuator/**";
}