package integrador_prog2.exception;

public class CampoVacioException extends Exception {

    public CampoVacioException() {
    }

    public CampoVacioException(String message) {
        super(message);
    }

    public CampoVacioException(String message, Throwable cause) {
        super(message, cause);
    }

    public CampoVacioException(Throwable cause) {
        super(cause);
    }

    public CampoVacioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
