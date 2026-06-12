package obeservacao.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationDataErrors>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var errors = ex.getFieldErrors();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errors.stream()
                        .map(ValidationDataErrors::new)
                        .toList());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<RestErrorMessage> handleUserException(UserException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(SolicitacaoException.class)
    public ResponseEntity<RestErrorMessage> handleSolicitacaoException(SolicitacaoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RestErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    private record RestErrorMessage(HttpStatus status, String message) {
    }

    private record ValidationDataErrors(
            String field,
            String message
    ) {
        private ValidationDataErrors(FieldError fe) {
            this(fe.getField(), fe.getDefaultMessage());
        }
    }
}
