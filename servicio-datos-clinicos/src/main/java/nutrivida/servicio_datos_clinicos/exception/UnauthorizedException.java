package nutrivida.servicio_datos_clinicos.exception;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message){
        super(message);
    }
}