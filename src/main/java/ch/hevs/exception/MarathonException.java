package ch.hevs.exception;

public class MarathonException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public MarathonException() {
		super();
	}

	public MarathonException(String arg0) {
		super(arg0);
	}

	public MarathonException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MarathonException(Throwable arg0) {
		super(arg0);
	}

}
