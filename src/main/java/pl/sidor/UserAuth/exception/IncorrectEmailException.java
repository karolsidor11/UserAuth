package pl.sidor.UserAuth.exception;

public class IncorrectEmailException extends Exception {

    private static final long serialVersionUID = 5197771352739480477L;

    public IncorrectEmailException() {
    }

    public IncorrectEmailException(String message) {
        super(message);
    }

    public IncorrectEmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
