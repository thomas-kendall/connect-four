package connect.four.web.api.model;

public class GameApiModel {

	private int id;
	private String player1;
	private String player2;
	private String gameStatus;
	private GameGridApiModel gameGrid;
	private GameResultApiModel gameResult;

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

	public String getPlayer1() {
		return player1;
	}

	public String getPlayer2() {
		return player2;
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

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
}
