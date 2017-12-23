package connect.four.bot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import connect.four.core.GameProperties;
import connect.four.core.GridLocation;
import connect.four.core.IGame;
import connect.four.core.utility.GridSearcher;
import connect.four.core.utility.MoveInformation;

public class BasicLogicBot implements IConnectFourBot {

	private String myToken;
	private String opponentToken;
	private Random random = new Random();

	public BasicLogicBot(String myToken, String opponentToken) {
		this.myToken = myToken;
		this.opponentToken = opponentToken;
	}

	private int chooseRandomColumn(List<GridLocation> availableLocations) {
		if (availableLocations.isEmpty()) {
			return -1;
		}
		return availableLocations.get(random.nextInt(availableLocations.size())).getCol();
	}

	private int findColumnMeetingSequenceLength(int sequenceLength,
			Map<GridLocation, List<MoveInformation>> locationMoveInfos) {
		for (Map.Entry<GridLocation, List<MoveInformation>> entry : locationMoveInfos.entrySet()) {
			for (MoveInformation moveInfo : entry.getValue()) {
				if (moveInfo.getImmediateSequenceLength() >= sequenceLength) {
					return entry.getKey().getCol();
				}
			}
		}

		return -1;
	}

	@Override
	public int makeDecision(IGame game) {
		int col = -1;
		List<GridLocation> availableLocations = game.getAvailableLocations();

		Map<GridLocation, List<MoveInformation>> myLocationMoveInfos = new HashMap<>();
		for (GridLocation gridLocation : availableLocations) {
			myLocationMoveInfos.put(gridLocation,
					GridSearcher.searchGridLocation(game.getGrid(), gridLocation, myToken));
		}

		Map<GridLocation, List<MoveInformation>> opponentLocationMoveInfos = new HashMap<>();
		for (GridLocation gridLocation : availableLocations) {
			opponentLocationMoveInfos.put(gridLocation,
					GridSearcher.searchGridLocation(game.getGrid(), gridLocation, opponentToken));
		}

		for (int sequenceLength = GameProperties.TARGET; sequenceLength > 1; sequenceLength--) {
			// Can I make X in a row?
			col = findColumnMeetingSequenceLength(sequenceLength, myLocationMoveInfos);
			if (col > -1) {
				break;
			}

			// Can I prevent my opponent from making X in a row?
			col = findColumnMeetingSequenceLength(sequenceLength, opponentLocationMoveInfos);
			if (col > -1) {
				break;
			}
		}

		if (col == -1) {
			// Drop randomly
			col = chooseRandomColumn(availableLocations);
		}

		return col;
	}

}
