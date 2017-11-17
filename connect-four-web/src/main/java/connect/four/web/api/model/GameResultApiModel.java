package connect.four.web.api.model;

import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;
import connect.four.core.result.WinnerResult;

public class GameResultApiModel {

	private String winner;

	public GameResultApiModel(IGameResult gameResult) {
		if (gameResult instanceof NoWinnerResult) {
			winner = null;
		} else if (gameResult instanceof WinnerResult) {
			WinnerResult wr = (WinnerResult) gameResult;
			winner = wr.getWinningPlayer().getName();
		}
	}

	public String getWinner() {
		return winner;
	}
}
