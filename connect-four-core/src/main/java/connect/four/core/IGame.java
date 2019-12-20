package connect.four.core;

import java.util.List;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.result.IGameResult;

public interface IGame {

	void dropChecker(String player, int col) throws ActionNotAllowedException;

	List<GameAction> getActions();

	List<Integer> getAvailableColumns();

	List<GridLocation> getAvailableLocations();

	String getCurrentPlayer();

	List<GameGrid> getGameGridHistory();

	IGameResult getGameResult();

	GameGrid getGrid();

	List<String> getPlayers();

	GameStatus getStatus();
}
