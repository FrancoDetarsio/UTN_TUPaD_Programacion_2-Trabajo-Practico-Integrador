package integrador_prog2.exception;

public class ProductoInexistenteException extends Exception {

    public ProductoInexistenteException() {
    }

    public ProductoInexistenteException(String message) {
        super(message);
    }

    public ProductoInexistenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductoInexistenteException(Throwable cause) {
        super(cause);
    }

    public ProductoInexistenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
