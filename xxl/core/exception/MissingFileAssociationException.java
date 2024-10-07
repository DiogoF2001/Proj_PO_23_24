package xxl.core.exception;

/**
 * Thrown when an application is not associated with a file
 */
public class MissingFileAssociationException extends Exception {
	/**
     * Constructor for MissingFileAssociationException with a message.
     * @param message The detailed message explaining the cause of the exception.
     */
    public MissingFileAssociationException(String message) {
        super(message);
    }

}
