package connect.four.core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;

public class Game implements IGame {

	private Random random = new Random();
	private GameGrid grid;
	private Queue<String> players;
	private GameStatus status;
	private GameCheckers checkers;
	private IGameResult gameResult;
	private List<GameAction> actions;

	public Game(String... players) {
		this.grid = new GameGrid();
		this.checkers = new GameCheckers(players);
		this.players = randomlyLoadPlayers(players);
		status = GameStatus.IN_PROGRESS;
		gameResult = null;
		actions = new ArrayList<>();
	}

	@Override
	public void dropChecker(String player, int col) throws ActionNotAllowedException, InvalidGridLocationException {
		// Validate game status
		if (status == GameStatus.COMPLETED) {
			throw new ActionNotAllowedException("The game is already completed.");
		}

		// Validate player
		if (player != getCurrentPlayer()) {
			throw new ActionNotAllowedException("It is not the turn for " + player);
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

	@Override
	public String getCurrentPlayer() {
		String result = null;
		if (getStatus() != GameStatus.COMPLETED) {
			result = players.peek();
		}
		return result;
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
	public List<String> getPlayers() {
		return new ArrayList<>(players);
	}

	@Override
	public GameStatus getStatus() {
		return status;
	}

	private Queue<String> randomlyLoadPlayers(String[] players) {
		Queue<String> result = new LinkedList<>();
		List<String> remainingPlayers = new ArrayList<>();
		for (String player : players) {
			remainingPlayers.add(player);
		}
		while (!remainingPlayers.isEmpty()) {
			result.add(remainingPlayers.remove(random.nextInt(remainingPlayers.size())));
		}
		return result;
	}

	private void setNextPlayer() {
		players.add(players.poll());
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
