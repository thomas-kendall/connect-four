package connect.four.core;

import java.util.ArrayList;
import java.util.List;

import connect.four.core.exception.ActionNotAllowedException;

public class GameCheckers {

	private List<Checker> checkers;

	public GameCheckers(String[] players) {
		checkers = new ArrayList<>();
		for (int i = 0; i < GameProperties.CHECKERS_PER_PLAYER; i++) {
			for (String player : players) {
				checkers.add(new Checker(player));
			}
		}
	}

	public boolean hasChecker(String player) {
		boolean result = false;
		for (Checker checker : checkers) {
			if (checker.getOwner().equals(player)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean isEmpty() {
		return checkers.isEmpty();
	}

	public Checker takeChecker(String player) throws ActionNotAllowedException {
		Checker result = null;

		// Find a checker
		for (Checker checker : checkers) {
			if (checker.getOwner().equals(player)) {
				result = checker;
				break;
			}
		}

		// Did they try to get one that doesn't exist?
		if (result == null) {
			throw new ActionNotAllowedException("There are no checkers available for this player.");
		}

		// Remove it from the list
		checkers.remove(result);

		return result;
	}
}
