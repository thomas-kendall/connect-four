package connect.four.core;

public class InvalidGridLocationException extends ConnectFourException {

	private static final long serialVersionUID = 7250204156240221448L;

	public InvalidGridLocationException(String message) {
		super(message);
	}

}
