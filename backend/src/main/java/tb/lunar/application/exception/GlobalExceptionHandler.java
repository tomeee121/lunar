package tb.lunar.application.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapacityExceededException.class)
    public ResponseEntity<Map<String,Object>> handleCapacity(CapacityExceededException ex) {
        return ResponseEntity.unprocessableEntity().body(Map.of(
                "error", "capacity_exceeded",
                "message", ex.getMessage()
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,Object>> handleConstraint(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "constraint_violation",
                "message", ex.getMessage()
        ));
    }
}