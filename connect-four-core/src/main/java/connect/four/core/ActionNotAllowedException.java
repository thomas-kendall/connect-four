package connect.four.core;

public class ActionNotAllowedException extends ConnectFourException {
	private static final long serialVersionUID = 1631048882902094913L;

	public ActionNotAllowedException(String message) {
		super(message);
	}

}
