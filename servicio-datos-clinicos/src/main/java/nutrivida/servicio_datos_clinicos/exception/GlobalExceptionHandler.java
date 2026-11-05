package nutrivida.servicio_datos_clinicos.exception;

import jakarta.servlet.http.HttpServletRequest;
import nutrivida.servicio_datos_clinicos.dto.ErrorResponse;
import nutrivida.servicio_datos_clinicos.dto.ErrorValidResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidResponse> manejarValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ){
        Map<String, String> errores = new HashMap<>();

        for(FieldError error : ex.getBindingResult().getFieldErrors()){
            errores.put(error.getField(), error.getDefaultMessage());
        }

        ErrorValidResponse response = ErrorValidResponse.builder()
                .fecha(LocalDateTime.now())
                .estado(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .ruta(request.getRequestURI())
                .errores(errores)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFound(NotFoundException ex, HttpServletRequest request){
        ErrorResponse response = crearError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> sinPermiso(ForbiddenException ex, HttpServletRequest request){
        ErrorResponse response = crearError(HttpStatus.FORBIDDEN, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> noAutorizado(UnauthorizedException ex, HttpServletRequest request){
        ErrorResponse response = crearError(HttpStatus.UNAUTHORIZED, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public ResponseEntity<ErrorResponse> servicioNoDisponible(
            ServicioNoDisponibleException ex,
            HttpServletRequest request
    ){
        ErrorResponse response = crearError(HttpStatus.SERVICE_UNAVAILABLE, ex.getMessage(), request);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> conflicto(HttpServletRequest request){
        ErrorResponse response = crearError(HttpStatus.CONFLICT,
                "No se pudo guardar el cambio. Vuelve a consultar la ficha.", request);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HandlerMethodValidationException.class})
    public ResponseEntity<ErrorResponse> peticionInvalida(HttpServletRequest request){
        ErrorResponse response = crearError(HttpStatus.BAD_REQUEST,
                "Revisa los tipos y parámetros enviados", request);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ErrorResponse crearError(HttpStatus estado, String mensaje, HttpServletRequest request){
        return ErrorResponse.builder()
                .fecha(LocalDateTime.now())
                .estado(estado.value())
                .mensaje(mensaje)
                .ruta(request.getRequestURI())
                .build();
    }
}