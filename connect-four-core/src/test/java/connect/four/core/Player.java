package connect.four.core;

public class Player implements IPlayer {

	private String name;
	private char token;

	public Player(String name, char token) {
		this.name = name;
		this.token = token;
	}

	@Override
	public String getName() {
		return name;
	}

	public char getToken() {
		return token;
	}
}
