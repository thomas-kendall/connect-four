package connect.four.web.api.model;

import java.util.List;

public class GameApiModel {

	private int id;
	private String gameStatus;
	private GameGridApiModel gameGrid;
	private GameResultApiModel gameResult;
	private List<GameActionApiModel> actions;

	public List<GameActionApiModel> getActions() {
		return actions;
	}

	public GameGridApiModel getGameGrid() {
		return gameGrid;
	}

	public GameResultApiModel getGameResult() {
		return gameResult;
	}

	public String getGameStatus() {
		return gameStatus;
	}

	public int getId() {
		return id;
	}

	public void setActions(List<GameActionApiModel> actions) {
		this.actions = actions;
	}

	public void setGameGrid(GameGridApiModel gameGrid) {
		this.gameGrid = gameGrid;
	}

	public void setGameResult(GameResultApiModel gameResult) {
		this.gameResult = gameResult;
	}

	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}

	public void setId(int id) {
		this.id = id;
	}

}
