package connect.four.core;

import java.util.ArrayList;
import java.util.List;

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
	private List<GameAction> actions;

	public Game(IPlayer... players) {
		this.grid = new GameGrid();
		this.checkers = new GameCheckers(players);
		this.players = players;
		currentPlayerIndex = 0;
		status = GameStatus.IN_PROGRESS;
		gameResult = null;
		actions = new ArrayList<>();
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

		// Store the action
		int rowPlayed = grid.getLowestRowPlayed(col);
		GameAction action = new GameAction(player, col, rowPlayed);
		actions.add(action);

		// Update game status
		updateGameStatus();

		// Set next player
		setNextPlayer();
	}

	@Override
	public List<GameAction> getActions() {
		return actions;
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
	public IPlayer getPlayer1() {
		return players[0];
	}

	@Override
	public IPlayer getPlayer2() {
		return players[1];
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
