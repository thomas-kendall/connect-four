package connect.four.core;

public class GameFactory {

	public static IGame createGame(IPlayer player1, IPlayer player2) {
		IGame game = new Game(player1, player2);
		return game;
	}
}
