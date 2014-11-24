package ee.ut.math.tvt.salessystem.domain.exception;

/**
 * Thrown when adding out of stock product to the shopping cart.
 */

public class OutOfStockException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs new <code>OutOfStockException</code>.
	 */
	public OutOfStockException() {
		super();
	}
	
	/**
	 * Constructs new <code>VerificationFailedOutOfStockException</code> with  with the specified detail message.
	 * @param message the detail message.
	 */
	public OutOfStockException(final String message) {
		super(message);
	}

}
