package connect.four.core;

public class GameAction {

	private String player;
	private int column;
	private int row;

	public GameAction(String player, int column, int row) {
		this.player = player;
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public String getPlayer() {
		return player;
	}

	public int getRow() {
		return row;
	}
}
