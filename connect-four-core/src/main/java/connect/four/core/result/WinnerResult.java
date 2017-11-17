package connect.four.core.result;

import connect.four.core.GridLocation;
import connect.four.core.IPlayer;

public class WinnerResult implements IGameResult {
	private IPlayer winningPlayer;
	private GridLocation[] gridLocations;

	public WinnerResult(IPlayer winningPlayer, GridLocation[] gridLocations) {
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
