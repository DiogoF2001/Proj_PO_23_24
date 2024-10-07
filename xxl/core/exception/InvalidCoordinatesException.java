package xxl.core.exception;

/**
 * Exception to indicate that the provided coordinates are invalid.
**/
public class InvalidCoordinatesException extends Exception{
	/**
     * Default constructor for InvalidCoordinatesException.
     */
    public InvalidCoordinatesException() {
        super();
    }

    /**
     * Constructor for InvalidCoordinatesException with a message.
     * @param message The detailed message explaining the cause of the exception.
     */
    public InvalidCoordinatesException(String message) {
        super(message);
    }

    /**
     * Constructor for InvalidCoordinatesException with a message and a cause.
     * @param message The detailed message explaining the cause of the exception.
     * @param cause The underlying cause of the exception.
     */
    public InvalidCoordinatesException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InvalidCoordinatesException with a cause.
     * @param cause The underlying cause of the exception.
     */
    public InvalidCoordinatesException(Throwable cause) {
        super(cause);
    }
}
