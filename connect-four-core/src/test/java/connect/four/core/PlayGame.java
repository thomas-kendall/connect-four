package connect.four.core;

import java.util.Scanner;

public class PlayGame {

	public static void main(String[] args) {
		new PlayGame();
	}

	private Player player1;
	private Player player2;
	private Player currentPlayer;
	private Scanner scanner;

	public PlayGame() {
		scanner = new Scanner(System.in);
		player1 = new Player("Player 1", 'X');
		player2 = new Player("Player 2", 'O');
		currentPlayer = player1;

		IGame game = GameFactory.createGame(player1, player2);
		while (game.getStatus() != GameStatus.COMPLETED) {
			// Render
			GameRenderer.renderGame(game);
			System.out.println();

			// Take a turn
			int col = getUserInputCol();
			game.dropChecker(currentPlayer, col);

			// Switch players
			nextPlayer();
		}
		// Render
		GameRenderer.renderGame(game);
		System.out.println();

		System.out.print("Game over. ");
		GameWinner winner = game.getWinner();
		if (winner == null) {
			System.out.println("Cat game!");
		} else {
			System.out.println(((Player) winner.getWinningPlayer()).getName() + " won.");
		}
	}

	private int getUserInputCol() {
		System.out.println(currentPlayer.getName() + ", it's your turn. Choose a column: ");
		return scanner.nextInt();
	}

	private void nextPlayer() {
		currentPlayer = currentPlayer == player1 ? player2 : player1;
	}
}
