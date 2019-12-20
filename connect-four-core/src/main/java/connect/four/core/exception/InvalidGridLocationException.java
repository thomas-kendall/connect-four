package connect.four.core.exception;

public class InvalidGridLocationException extends RuntimeException {

	private static final long serialVersionUID = 7250204156240221448L;

	public InvalidGridLocationException(String message) {
		super(message);
	}

}
