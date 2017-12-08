package connect.four.core;

public class GameFactory {

	public static IGame createGame(String player1, String player2) {
		IGame game = new Game(player1, player2);
		return game;
	}
}
