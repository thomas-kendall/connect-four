package connect.four.web.entity;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "GameStateResults")
public class GameStateResult {

	@Id
	@DynamoDBHashKey
	private String gameState;

	private int winCount;

	private int lossCount;

	private int tieCount;

	public GameStateResult() {
	}

	public GameStateResult(String gameState, int winCount, int lossCount, int tieCount) {
		this.gameState = gameState;
		this.winCount = winCount;
		this.lossCount = lossCount;
		this.tieCount = tieCount;
	}

	public String getGameState() {
		return gameState;
	}

	public int getLossCount() {
		return lossCount;
	}

	public int getTieCount() {
		return tieCount;
	}

	public int getWinCount() {
		return winCount;
	}

	public void incrementLossCount() {
		lossCount++;
	}

	public void incrementTieCount() {
		tieCount++;
	}

	public void incrementWinCount() {
		winCount++;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public void setLossCount(int lossCount) {
		this.lossCount = lossCount;
	}

	public void setTieCount(int tieCount) {
		this.tieCount = tieCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

}
