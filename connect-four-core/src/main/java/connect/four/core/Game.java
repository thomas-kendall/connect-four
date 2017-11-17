package connect.four.core;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;

public class Game implements IGame {

	private GameGrid grid;
	private IPlayer[] players;
	private int currentPlayerIndex;
	private GameStatus status;
	private GameCheckers checkers;
	private IGameResult gameResult;

	public Game(IPlayer... players) {
		this.grid = new GameGrid();
		this.checkers = new GameCheckers(players);
		this.players = players;
		currentPlayerIndex = 0;
		status = GameStatus.IN_PROGRESS;
		gameResult = null;
	}

	@Override
	public void dropChecker(IPlayer player, int col) throws ActionNotAllowedException, InvalidGridLocationException {
		// Validate game status
		if (status == GameStatus.COMPLETED) {
			throw new ActionNotAllowedException("The game is already completed.");
		}

		// Validate player
		if (player != getCurrentPlayer()) {
			throw new ActionNotAllowedException("It is not the turn for " + player.getName());
		}

		// Drop checker
		Checker checker = checkers.takeChecker(player);
		grid.dropChecker(col, checker);

		// Update game status
		updateGameStatus();

		// Set next player
		setNextPlayer();
	}

	private IPlayer getCurrentPlayer() {
		return players[currentPlayerIndex];
	}

	@Override
	public IGameResult getGameResult() {
		return gameResult;
	}

	@Override
	public GameGrid getGrid() {
		return grid;
	}

	@Override
	public GameStatus getStatus() {
		return status;
	}

	private void setNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex >= players.length) {
			currentPlayerIndex = 0;
		}
	}

	private void updateGameStatus() {
		// Is there a winner?
		gameResult = grid.getWinner();
		if (gameResult != null) {
			status = GameStatus.COMPLETED;
		}

		// Are we out of checkers?
		else if (checkers.isEmpty()) {
			status = GameStatus.COMPLETED;
			gameResult = new NoWinnerResult();
		}
	}

}
