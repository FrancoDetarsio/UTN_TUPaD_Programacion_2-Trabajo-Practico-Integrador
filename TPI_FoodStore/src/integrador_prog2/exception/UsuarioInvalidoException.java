package integrador_prog2.exception;

public class UsuarioInvalidoException extends Exception {

    public UsuarioInvalidoException() {
    }

    public UsuarioInvalidoException(String message) {
        super(message);
    }

    public UsuarioInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioInvalidoException(Throwable cause) {
        super(cause);
    }

    public UsuarioInvalidoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
