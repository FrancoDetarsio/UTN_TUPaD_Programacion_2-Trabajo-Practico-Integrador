package integrador_prog2.exception;

public class RespuestaInvalidaException extends Exception {

    public RespuestaInvalidaException() {
    }

    public RespuestaInvalidaException(String message) {
        super(message);
    }

    public RespuestaInvalidaException(String message, Throwable cause) {
        super(message, cause);
    }

    public RespuestaInvalidaException(Throwable cause) {
        super(cause);
    }

    public RespuestaInvalidaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
