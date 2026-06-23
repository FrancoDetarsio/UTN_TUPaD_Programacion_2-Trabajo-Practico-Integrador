package integrador_prog2.exception;

public class RelacionException extends Exception {

    public RelacionException() {
    }

    public RelacionException(String message) {
        super(message);
    }

    public RelacionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RelacionException(Throwable cause) {
        super(cause);
    }

    public RelacionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
