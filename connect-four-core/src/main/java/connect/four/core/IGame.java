package connect.four.core;

public interface IGame {

	void dropChecker(IPlayer player, int col);

	GameGrid getGrid();

	GameStatus getStatus();

	GameWinner getWinner();
}
