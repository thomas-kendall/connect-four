package connect.four.web.api.model;

import connect.four.core.GameProperties;
import connect.four.core.IGame;
import connect.four.core.exception.InvalidGridLocationException;

public class GameGridApiModel {
	private String[][] rows;

	public GameGridApiModel(IGame game) {
		try {
			rows = new String[GameProperties.ROWS][GameProperties.COLS];
			for (int row = 0; row < GameProperties.ROWS; row++) {
				for (int col = 0; col < GameProperties.COLS; col++) {
					rows[row][col] = game.getGrid().getOwner(row, col);
				}
			}
		} catch (InvalidGridLocationException e) {
			throw new RuntimeException(e);
		}
	}

	public String[][] getRows() {
		return rows;
	}

	public void setRows(String[][] rows) {
		this.rows = rows;
	}
}
