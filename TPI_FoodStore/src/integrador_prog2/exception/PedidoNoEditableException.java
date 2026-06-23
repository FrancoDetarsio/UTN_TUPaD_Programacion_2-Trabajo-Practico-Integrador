package integrador_prog2.exception;

public class PedidoNoEditableException extends Exception {

    public PedidoNoEditableException() {
    }

    public PedidoNoEditableException(String message) {
        super(message);
    }

    public PedidoNoEditableException(String message, Throwable cause) {
        super(message, cause);
    }

    public PedidoNoEditableException(Throwable cause) {
        super(cause);
    }

    public PedidoNoEditableException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
  
}
