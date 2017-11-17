package connect.four.core;

public class GameGrid {

	private Checker[][] grid;

	public GameGrid() {
		grid = new Checker[GameProperties.ROWS][GameProperties.COLS];
	}

	public void dropChecker(int col, Checker checker) {
		validateCol(col);

		// Find the open row
		int openRow = -1;
		for (int row = 0; row < GameProperties.ROWS; row++) {
			if (grid[row][col] == null) {
				openRow = row;
				break;
			}
		}
		if (openRow == -1) {
			throw new IllegalArgumentException("No openings in col " + col);
		}

		grid[openRow][col] = checker;
	}

	public Checker getChecker(int row, int col) {
		validateRowCol(row, col);
		return grid[row][col];
	}

	private IPlayer getOwner(int row, int col) {
		IPlayer owner = null;
		Checker checker = getChecker(row, col);
		if (checker != null) {
			owner = checker.getOwner();
		}
		return owner;
	}

	public GameWinner getWinner() {
		GameWinner result = null;

		for (int row = 0; row < GameProperties.ROWS; row++) {
			for (int col = 0; col < GameProperties.COLS; col++) {
				// Find horizontal
				result = getWinnerHorizontal(row, col);
				if (result != null) {
					return result;
				}

				// Find vertical
				result = getWinnerVertical(row, col);
				if (result != null) {
					return result;
				}

				// Find diagonal (down-right)
				result = getWinnerDiagonalDownRight(row, col);
				if (result != null) {
					return result;
				}

				// Find diagonal (up-right)
				result = getWinnerDiagonalUpRight(row, col);
				if (result != null) {
					return result;
				}
			}
		}

		return null;
	}

	private GameWinner getWinnerDiagonalDownRight(int row, int col) {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		if (row < GameProperties.TARGET - 1) {
			return null;
		}
		IPlayer player = getOwner(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row - i, col + i);
			IPlayer p = getOwner(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new GameWinner(player, locations);
	}

	private GameWinner getWinnerDiagonalUpRight(int row, int col) {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		if (row > GameProperties.ROWS - GameProperties.TARGET) {
			return null;
		}
		IPlayer player = getOwner(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row + i, col + i);
			IPlayer p = getOwner(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new GameWinner(player, locations);
	}

	private GameWinner getWinnerHorizontal(int row, int col) {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		IPlayer player = getOwner(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row, col + i);
			IPlayer p = getOwner(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new GameWinner(player, locations);
	}

	private GameWinner getWinnerVertical(int row, int col) {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (row > GameProperties.ROWS - GameProperties.TARGET) {
			return null;
		}
		IPlayer player = getOwner(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row + i, col);
			IPlayer p = getOwner(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new GameWinner(player, locations);
	}

	private void validateCol(int col) {
		if (col < 0 || col >= GameProperties.COLS) {
			throw new IllegalArgumentException("col must be between 0 and " + GameProperties.COLS);
		}
	}

	private void validateRow(int row) {
		if (row < 0 || row >= GameProperties.ROWS) {
			throw new IllegalArgumentException("row must be between 0 and " + GameProperties.ROWS);
		}
	}

	private void validateRowCol(int row, int col) {
		validateRow(row);
		validateCol(col);
	}
}