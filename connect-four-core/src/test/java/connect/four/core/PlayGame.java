package connect.four.core;

import java.util.Scanner;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;
import connect.four.core.result.WinnerResult;

public class PlayGame {

	public static void main(String[] args) throws ActionNotAllowedException, InvalidGridLocationException {
		new PlayGame();
	}

	private String player1;
	private String player2;
	private String currentPlayer;
	private Scanner scanner;

	public PlayGame() throws ActionNotAllowedException, InvalidGridLocationException {
		scanner = new Scanner(System.in);
		player1 = "X";
		player2 = "O";
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
		IGameResult gameResult = game.getGameResult();
		if (gameResult instanceof NoWinnerResult) {
			System.out.println("Cat game!");
		} else {
			WinnerResult winnerResult = (WinnerResult) gameResult;
			System.out.println(winnerResult.getWinningPlayer() + " won.");
		}
	}

	private int getUserInputCol() {
		System.out.println(currentPlayer + ", it's your turn. Choose a column: ");
		return scanner.nextInt();
	}

	private void nextPlayer() {
		currentPlayer = currentPlayer == player1 ? player2 : player1;
	}
}
