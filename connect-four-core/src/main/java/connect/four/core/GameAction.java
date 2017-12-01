package connect.four.core;

public class GameAction {

	private IPlayer player;
	private int column;
	private int row;

	public GameAction(IPlayer player, int column, int row) {
		this.player = player;
		this.column = column;
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public IPlayer getPlayer() {
		return player;
	}

	public int getRow() {
		return row;
	}
}
