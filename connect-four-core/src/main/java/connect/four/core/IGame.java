package connect.four.core;

public interface IGame {

	void dropChecker(IPlayer player, int col) throws ActionNotAllowedException, InvalidGridLocationException;

	GameGrid getGrid();

	GameStatus getStatus();

	GameWinner getWinner();
}
