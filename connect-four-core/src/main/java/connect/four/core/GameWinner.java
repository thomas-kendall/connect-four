package connect.four.core;

public class GameWinner {
	private IPlayer winningPlayer;
	private GridLocation[] gridLocations;

	public GameWinner(IPlayer winningPlayer, GridLocation[] gridLocations) {
		this.winningPlayer = winningPlayer;
		this.gridLocations = gridLocations;
	}

	public GridLocation[] getGridLocations() {
		return gridLocations;
	}

	public IPlayer getWinningPlayer() {
		return winningPlayer;
	}
}
