package connect.four.core.utility;

import java.util.ArrayList;
import java.util.List;

import connect.four.core.GameGrid;
import connect.four.core.GameProperties;
import connect.four.core.GridLocation;
import connect.four.core.exception.InvalidGridLocationException;

public class GridSearcher {

	// Counts the number matching tokens directly below the given location
	public static MoveInformation countBelow(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol(), row = location.getRow() - 1; row > -1; row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol(), row = location.getRow() - 1; row > -1; row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Counts the number matching tokens diagonally down and to the left of the
	// given location
	public static MoveInformation countDiagonalDownLeft(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow() - 1; col > -1 && row > -1; col--, row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow() - 1; col > -1 && row > -1; col--, row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Counts the number matching tokens diagonally down and to the right of the
	// given location
	public static MoveInformation countDiagonalDownRight(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow() - 1; col < GameProperties.COLS
				&& row > -1; col++, row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow() - 1; col < GameProperties.COLS
				&& row > -1; col++, row--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Counts the number matching tokens diagonally up and to the left of the
	// given location
	public static MoveInformation countDiagonalUpLeft(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow() + 1; col > -1
				&& row < GameProperties.ROWS; col--, row++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow() + 1; col > -1
				&& row < GameProperties.ROWS; col--, row++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Counts the number matching tokens diagonally up and to the right of the
	// given location
	public static MoveInformation countDiagonalUpRight(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow() + 1; col < GameProperties.COLS
				&& row < GameProperties.ROWS; col++, row++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow() + 1; col < GameProperties.COLS
				&& row < GameProperties.ROWS; col++, row++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Counts the number matching tokens directly to the left of the given location
	public static MoveInformation countLeft(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow(); col > -1; col--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() - 1, row = location.getRow(); col > -1; col--) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	public static MoveInformation countPotentialSequenceDiagonalBottomLeftTopRight(GameGrid grid, GridLocation location,
			String token) {
		return countDiagonalDownLeft(grid, location, token).add(MoveInformation.SINGLE_LOCATION)
				.add(countDiagonalUpRight(grid, location, token));
	}

	public static MoveInformation countPotentialSequenceDiagonalTopLeftBottomRight(GameGrid grid, GridLocation location,
			String token) {
		return countDiagonalUpLeft(grid, location, token).add(MoveInformation.SINGLE_LOCATION)
				.add(countDiagonalDownRight(grid, location, token));
	}

	// Counts the horizontal length of the potential sequence if checker is played
	// at the given location
	public static MoveInformation countPotentialSequenceHorizontal(GameGrid grid, GridLocation location, String token) {
		return countLeft(grid, location, token).add(MoveInformation.SINGLE_LOCATION)
				.add(countRight(grid, location, token));
	}

	public static MoveInformation countPotentialSequenceVertical(GameGrid grid, GridLocation location, String token) {
		return countBelow(grid, location, token).add(MoveInformation.SINGLE_LOCATION);
	}

	// Counts the number matching tokens directly to the right of the given location
	public static MoveInformation countRight(GameGrid grid, GridLocation location, String token) {
		int immediateCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow(); col < GameProperties.COLS; col++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (token.equals(gridToken)) {
					immediateCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		int potentialCount = 0;
		for (int col = location.getCol() + 1, row = location.getRow(); col < GameProperties.COLS; col++) {
			try {
				String gridToken = grid.getChecker(row, col);
				if (gridToken == null || token.equals(gridToken)) {
					potentialCount++;
				} else {
					break;
				}
			} catch (InvalidGridLocationException e) {
				throw new RuntimeException(e);
			}
		}

		return new MoveInformation(immediateCount, potentialCount);
	}

	// Returns a list of MoveInformations, one for each direction: Horizontal,
	// Vertical, Diagonal and Diagonal
	public static List<MoveInformation> searchGridLocation(GameGrid grid, GridLocation location, String token) {
		List<MoveInformation> moveInfos = new ArrayList<>();

		moveInfos.add(countPotentialSequenceHorizontal(grid, location, token));
		moveInfos.add(countPotentialSequenceVertical(grid, location, token));
		moveInfos.add(countPotentialSequenceDiagonalTopLeftBottomRight(grid, location, token));
		moveInfos.add(countPotentialSequenceDiagonalBottomLeftTopRight(grid, location, token));

		return moveInfos;
	}
}
