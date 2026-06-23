package integrador_prog2.exception;

public class EntidadNoEncontradaException extends Exception {

    public EntidadNoEncontradaException() {
    }

    public EntidadNoEncontradaException(String message) {
        super(message);
    }

    public EntidadNoEncontradaException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntidadNoEncontradaException(Throwable cause) {
        super(cause);
    }

    public EntidadNoEncontradaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
