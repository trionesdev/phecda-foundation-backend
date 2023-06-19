package ms.phecda.edge.ex;

public class EdgeException extends RuntimeException{
    public EdgeException() {
        super();
    }

    public EdgeException(String message) {
        super(message);
    }

    public EdgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public EdgeException(Throwable cause) {
        super(cause);
    }

    protected EdgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
