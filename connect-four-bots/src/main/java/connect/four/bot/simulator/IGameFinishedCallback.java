package connect.four.bot.simulator;

import connect.four.core.IGame;

public interface IGameFinishedCallback {

	void onGameFinished(IGame game);
}
