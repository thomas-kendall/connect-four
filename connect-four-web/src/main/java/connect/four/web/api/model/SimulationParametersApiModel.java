package connect.four.web.api.model;

public class SimulationParametersApiModel {
	private BotType botX;
	private BotType botO;
	private int numberOfGames;
	private int bucketSize;

	public BotType getBotO() {
		return botO;
	}

	public BotType getBotX() {
		return botX;
	}

	public int getBucketSize() {
		return bucketSize;
	}

	public int getNumberOfGames() {
		return numberOfGames;
	}

	public void setBotO(BotType botO) {
		this.botO = botO;
	}

	public void setBotX(BotType botX) {
		this.botX = botX;
	}

	public void setBucketSize(int bucketSize) {
		this.bucketSize = bucketSize;
	}

	public void setNumberOfGames(int numberOfGames) {
		this.numberOfGames = numberOfGames;
	}
}
