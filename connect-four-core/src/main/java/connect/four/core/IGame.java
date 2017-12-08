package connect.four.core;

import java.util.List;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.core.result.IGameResult;

public interface IGame {

	void dropChecker(String player, int col) throws ActionNotAllowedException, InvalidGridLocationException;

	List<GameAction> getActions();

	String getCurrentPlayer();

	IGameResult getGameResult();

	GameGrid getGrid();

	String getPlayer1();

	String getPlayer2();

	GameStatus getStatus();
}
