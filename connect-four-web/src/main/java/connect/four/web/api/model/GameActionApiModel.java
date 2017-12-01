package connect.four.web.api.model;

import connect.four.core.GameAction;

public class GameActionApiModel {
	private String player;
	private int col;
	private int row;

	public GameActionApiModel(GameAction action) {
		this.player = action.getPlayer().getName();
		this.col = action.getColumn();
		this.row = action.getRow();
	}

	public int getCol() {
		return col;
	}

	public String getPlayer() {
		return player;
	}

	public int getRow() {
		return row;
	}
}
