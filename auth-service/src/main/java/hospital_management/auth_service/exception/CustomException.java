package hospital_management.auth_service.exception;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Date;


//@Data

//@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private Date timestamp;
    private String message;
    private HttpStatus status;

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}