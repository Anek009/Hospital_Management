package hospital_management.auth_service.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneralException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<?> emailExistsException(Exception ex){
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}

