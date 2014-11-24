package ee.ut.math.tvt.salessystem.domain.exception;

/**
 * Thrown when domain controller's verification fails.
 */
public class VerificationFailedException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructs new <code>VerificationFailedException</code>.
	 */
	public VerificationFailedException() {
		super();
	}
	
	/**
	 * Constructs new <code>VerificationFailedException</code> with  with the specified detail message.
	 * @param message the detail message.
	 */
	public VerificationFailedException(final String message) {
		super(message);
	}
}
