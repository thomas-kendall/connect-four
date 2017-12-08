package connect.four.core.result;

import connect.four.core.GridLocation;

public class WinnerResult implements IGameResult {
	private String winningPlayer;
	private GridLocation[] gridLocations;

	public WinnerResult(String winningPlayer, GridLocation[] gridLocations) {
		this.winningPlayer = winningPlayer;
		this.gridLocations = gridLocations;
	}

	public GridLocation[] getGridLocations() {
		return gridLocations;
	}

	public String getWinningPlayer() {
		return winningPlayer;
	}
}
