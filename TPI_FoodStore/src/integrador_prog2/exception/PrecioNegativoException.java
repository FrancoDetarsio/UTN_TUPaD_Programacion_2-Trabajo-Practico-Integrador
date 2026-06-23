package integrador_prog2.exception;

public class PrecioNegativoException extends Exception {

    public PrecioNegativoException() {
    }

    public PrecioNegativoException(String message) {
        super(message);
    }

    public PrecioNegativoException(String message, Throwable cause) {
        super(message, cause);
    }

    public PrecioNegativoException(Throwable cause) {
        super(cause);
    }

    public PrecioNegativoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
     
}
