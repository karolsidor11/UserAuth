package pl.sidor.UserAuth.exception;

public class IncorrectIDException extends Exception {

    private static final long serialVersionUID = 3698326789958804754L;

    public IncorrectIDException() {
    }

    public IncorrectIDException(String message) {
        super(message);
    }

    public IncorrectIDException(String message, Throwable cause) {
        super(message, cause);
    }
}
