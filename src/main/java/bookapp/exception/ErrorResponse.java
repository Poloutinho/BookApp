package bookapp.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String message;

    public ErrorResponse(HttpStatus httpStatus, String message) {
        this.status = status;
        this.message = message;
    }
}
