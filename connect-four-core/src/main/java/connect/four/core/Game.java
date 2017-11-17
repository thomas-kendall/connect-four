package connect.four.core;

public class Game implements IGame {

	private GameGrid grid;
	private IPlayer[] players;
	private int currentPlayerIndex;
	private GameStatus status;
	private GameCheckers checkers;
	private GameWinner winner;

	public Game(IPlayer... players) {
		this.grid = new GameGrid();
		this.checkers = new GameCheckers(players);
		this.players = players;
		currentPlayerIndex = 0;
		status = GameStatus.IN_PROGRESS;
		winner = null;
	}

	@Override
	public void dropChecker(IPlayer player, int col) {
		// Validate game status
		if (status == GameStatus.COMPLETED) {
			// TODO: throw exception
		}

		// Validate player
		if (player != getCurrentPlayer()) {
			// TODO: throw exception
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
	public GameGrid getGrid() {
		return grid;
	}

	@Override
	public GameStatus getStatus() {
		return status;
	}

	@Override
	public GameWinner getWinner() {
		return winner;
	}

	private void setNextPlayer() {
		currentPlayerIndex++;
		if (currentPlayerIndex >= players.length) {
			currentPlayerIndex = 0;
		}
	}

	private void updateGameStatus() {
		// Is there a winner?
		winner = grid.getWinner();
		if (winner != null) {
			status = GameStatus.COMPLETED;
		}

		// Are we out of checkers?
		if (checkers.isEmpty()) {
			status = GameStatus.COMPLETED;
		}
	}

}
