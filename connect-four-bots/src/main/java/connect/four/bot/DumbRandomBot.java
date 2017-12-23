package connect.four.bot;

import java.util.List;
import java.util.Random;

import connect.four.core.IGame;

public class DumbRandomBot implements IConnectFourBot {

	private Random random = new Random();

	@Override
	public int makeDecision(IGame game) {
		List<Integer> availableColumns = game.getAvailableColumns();
		if (availableColumns.isEmpty()) {
			return -1;
		}
		return availableColumns.get(random.nextInt(availableColumns.size()));
	}

}
