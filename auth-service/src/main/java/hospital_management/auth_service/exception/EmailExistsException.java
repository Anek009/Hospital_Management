package hospital_management.auth_service.exception;

public class EmailExistsException extends Exception{
    public EmailExistsException(String message){
        super(message);
    }
}
