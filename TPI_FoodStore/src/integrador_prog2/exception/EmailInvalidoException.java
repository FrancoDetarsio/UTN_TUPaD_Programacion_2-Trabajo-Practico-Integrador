package integrador_prog2.exception;

public class EmailInvalidoException extends Exception {

    public EmailInvalidoException() {
    }

    public EmailInvalidoException(String message) {
        super(message);
    }

    public EmailInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailInvalidoException(Throwable cause) {
        super(cause);
    }

    public EmailInvalidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
