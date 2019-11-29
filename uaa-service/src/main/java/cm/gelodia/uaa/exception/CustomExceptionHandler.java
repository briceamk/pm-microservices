package cm.gelodia.uaa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConflictException.class)
    protected final ResponseEntity<ExceptionInfoDetails> handleConflictException(ConflictException e,
                                                                                 WebRequest request) {
        ExceptionInfoDetails errorDetails = new ExceptionInfoDetails(HttpStatus.CONFLICT.value(),
                LocalDateTime.now(), e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected final ResponseEntity<ExceptionInfoDetails> handleResourceNotFoundException(ResourceNotFoundException e,
                                                                                 WebRequest request) {
        ExceptionInfoDetails errorDetails = new ExceptionInfoDetails(HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    protected final ResponseEntity<ExceptionInfoDetails> handleBadRequestException(BadRequestException e,
                                                                           WebRequest request) {
        ExceptionInfoDetails errorDetails = new ExceptionInfoDetails(HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    protected final ResponseEntity<ExceptionInfoDetails> handleAppException(AppException e,
                                                                    WebRequest request) {
        ExceptionInfoDetails errorDetails = new ExceptionInfoDetails(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),  e.getLocalizedMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
