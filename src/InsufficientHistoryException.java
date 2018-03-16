

public class InsufficientHistoryException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InsufficientHistoryException() {
        System.err.println("History not shorter than m.");
    }
}
