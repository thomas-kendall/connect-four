package connect.four.bot.simulator;

import connect.four.bot.IConnectFourBot;
import connect.four.core.GameFactory;
import connect.four.core.GameStatus;
import connect.four.core.IGame;
import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;

public class BotSimulator {

	private IConnectFourBot playerX;
	private IConnectFourBot playerO;

	public BotSimulator(IConnectFourBot playerX, IConnectFourBot playerO) {
		this.playerX = playerX;
		this.playerO = playerO;
	}

	public void simulateSeries(int numberOfGames, IGameFinishedCallback gameFinishedCallback)
			throws ActionNotAllowedException, InvalidGridLocationException {
		for (int i = 0; i < numberOfGames; i++) {
			IGame game = GameFactory.createGame("X", "O");
			while (game.getStatus() != GameStatus.COMPLETED) {
				IConnectFourBot currentPlayerBot = game.getCurrentPlayer().equals("X") ? playerX : playerO;
				int columnToPlay = currentPlayerBot.makeDecision(game);
				game.dropChecker(game.getCurrentPlayer(), columnToPlay);
			}
			gameFinishedCallback.onGameFinished(game);
		}
	}
}
