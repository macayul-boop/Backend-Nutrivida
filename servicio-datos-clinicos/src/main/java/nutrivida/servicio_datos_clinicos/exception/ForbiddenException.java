package nutrivida.servicio_datos_clinicos.exception;

public class ForbiddenException extends RuntimeException {

    public ForbiddenException(String message){
        super(message);
    }
}