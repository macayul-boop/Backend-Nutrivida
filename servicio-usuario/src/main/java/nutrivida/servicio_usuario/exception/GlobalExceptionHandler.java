package nutrivida.servicio_usuario.exception;

import jakarta.servlet.http.HttpServletRequest;
import nutrivida.servicio_usuario.dto.ErrorResponse;
import nutrivida.servicio_usuario.dto.ErrorValidResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Exceptiones de la dependencia validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidResponse> manejarValidacion(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }

        ErrorValidResponse response = ErrorValidResponse.builder()
                .fecha(LocalDateTime.now())
                .estado(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request hoka")
                .ruta(request.getRequestURI())
                .errores(errores)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .estado(HttpStatus.NOT_FOUND.value())
                .mensaje(ex.getMessage())
                .ruta(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ConflictoException.class)
    public ResponseEntity<ErrorResponse> conflicto(ConflictoException ex, HttpServletRequest request){
        ErrorResponse error = ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .estado(HttpStatus.CONFLICT.value())
                .mensaje(ex.getMessage())
                .ruta(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }



}
