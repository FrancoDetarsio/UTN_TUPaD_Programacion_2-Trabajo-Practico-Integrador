package integrador_prog2.exception;

public class CategoriaExistenteException extends Exception {

    public CategoriaExistenteException() {
    }

    public CategoriaExistenteException(String message) {
        super(message);
    }

    public CategoriaExistenteException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoriaExistenteException(Throwable cause) {
        super(cause);
    }

    public CategoriaExistenteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
   
}
