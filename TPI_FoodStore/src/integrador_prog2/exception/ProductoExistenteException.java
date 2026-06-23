package integrador_prog2.exception;

public class ProductoExistenteException extends Exception {

    public ProductoExistenteException() {
    }

    public ProductoExistenteException(String message) {
        super(message);
    }

    public ProductoExistenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductoExistenteException(Throwable cause) {
        super(cause);
    }

    public ProductoExistenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
