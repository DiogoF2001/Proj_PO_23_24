package xxl.core.exception;

/**
 * Exception to indicate that the provided range format is invalid.
 */
public class InvalidRangeFormatException extends Exception {
    
    /**
     * Default constructor for InvalidRangeFormatException.
     */
    public InvalidRangeFormatException() {
        super();
    }

    /**
     * Constructor for InvalidRangeFormatException with a message.
     * @param message The detailed message explaining the cause of the exception.
     */
    public InvalidRangeFormatException(String message) {
        super(message);
    }

    /**
     * Constructor for InvalidRangeFormatException with a message and a cause.
     * @param message The detailed message explaining the cause of the exception.
     * @param cause The underlying cause of the exception.
     */
    public InvalidRangeFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor for InvalidRangeFormatException with a cause.
     * @param cause The underlying cause of the exception.
     */
    public InvalidRangeFormatException(Throwable cause) {
        super(cause);
    }
}
