package connect.four.core;

import java.util.ArrayList;
import java.util.List;

import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;
import connect.four.core.result.WinnerResult;

public class GameGrid {

	private String[][] grid;

	public GameGrid() {
		grid = new String[GameProperties.ROWS][GameProperties.COLS];
	}

	@Override
	public GameGrid clone() {
		GameGrid clone = new GameGrid();
		for (int row = 0; row < GameProperties.ROWS; row++) {
			for (int col = 0; col < GameProperties.COLS; col++) {
				clone.grid[row][col] = grid[row][col];
			}
		}
		return clone;
	}

	public void dropChecker(int col, String playerToken) throws InvalidGridLocationException {
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
			throw new InvalidGridLocationException("No openings in col " + col);
		}

		grid[openRow][col] = playerToken;
	}

	public List<Integer> getAvailableColumns() {
		List<Integer> availableColumns = new ArrayList<>();
		for (int col = 0; col < GameProperties.COLS; col++) {
			try {
				if (getChecker(GameProperties.TOP_ROW_INDEX, col) == null) {
					availableColumns.add(col);
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}
		return availableColumns;
	}

	public List<GridLocation> getAvailableLocations() {
		List<GridLocation> availableLocations = new ArrayList<>();
		for (int col = 0; col < GameProperties.COLS; col++) {
			int rowPlayed = getLowestRowPlayed(col);
			if (rowPlayed != GameProperties.TOP_ROW_INDEX) {
				availableLocations.add(new GridLocation(rowPlayed + 1, col));
			}
		}
		return availableLocations;
	}

	public String getChecker(int row, int col) {
		validateRowCol(row, col);
		return grid[row][col];
	}

	public IGameResult getGameResult() {
		IGameResult result = null;
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

		// Are there any available spaces?
		for (int row = 0; row < GameProperties.ROWS; row++) {
			for (int col = 0; col < GameProperties.COLS; col++) {
				if (grid[row][col] == null) {
					return null;
				}
			}
		}

		return new NoWinnerResult();
	}

	public int getLowestRowPlayed(int col) {
		int result = -1;
		for (int row = 0; row < GameProperties.ROWS; row++) {
			if (grid[row][col] == null) {
				break;
			} else {
				result = row;
			}
		}
		return result;
	}

	private IGameResult getWinnerDiagonalDownRight(int row, int col) throws InvalidGridLocationException {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		if (row < GameProperties.TARGET - 1) {
			return null;
		}
		String player = getChecker(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row - i, col + i);
			String p = getChecker(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new WinnerResult(player, locations);
	}

	private IGameResult getWinnerDiagonalUpRight(int row, int col) throws InvalidGridLocationException {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		if (row > GameProperties.ROWS - GameProperties.TARGET) {
			return null;
		}
		String player = getChecker(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row + i, col + i);
			String p = getChecker(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new WinnerResult(player, locations);
	}

	private IGameResult getWinnerHorizontal(int row, int col) throws InvalidGridLocationException {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (col > GameProperties.COLS - GameProperties.TARGET) {
			return null;
		}
		String player = getChecker(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row, col + i);
			String p = getChecker(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new WinnerResult(player, locations);
	}

	private IGameResult getWinnerVertical(int row, int col) throws InvalidGridLocationException {
		GridLocation[] locations = new GridLocation[GameProperties.TARGET];
		if (row > GameProperties.ROWS - GameProperties.TARGET) {
			return null;
		}
		String player = getChecker(row, col);
		if (player == null) {
			return null;
		}
		locations[0] = new GridLocation(row, col);
		for (int i = 1; i < GameProperties.TARGET; i++) {
			locations[i] = new GridLocation(row + i, col);
			String p = getChecker(locations[i].getRow(), locations[i].getCol());
			if (!player.equals(p)) {
				return null;
			}
		}

		return new WinnerResult(player, locations);
	}

	private void validateCol(int col) throws InvalidGridLocationException {
		if (col < 0 || col >= GameProperties.COLS) {
			throw new InvalidGridLocationException("col must be between 0 and " + GameProperties.COLS);
		}
	}

	private void validateRow(int row) throws InvalidGridLocationException {
		if (row < 0 || row >= GameProperties.ROWS) {
			throw new InvalidGridLocationException("row must be between 0 and " + GameProperties.ROWS);
		}
	}

	private void validateRowCol(int row, int col) throws InvalidGridLocationException {
		validateRow(row);
		validateCol(col);
	}
}
