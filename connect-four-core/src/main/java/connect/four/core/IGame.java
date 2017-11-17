package connect.four.core;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;

public interface IGame {

	void dropChecker(IPlayer player, int col) throws ActionNotAllowedException, InvalidGridLocationException;

	GameGrid getGrid();

	GameStatus getStatus();

	IGameResult getGameResult();
}
