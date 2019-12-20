package connect.four.core.exception;

public class ActionNotAllowedException extends Exception {
	private static final long serialVersionUID = 1631048882902094913L;

	public ActionNotAllowedException(String message) {
		super(message);
	}

}
