package connect.four.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import connect.four.core.IGame;
import connect.four.core.result.WinnerResult;

@Service
public class StatisticsService {

	private List<Boolean> winsAndLosses = new ArrayList<>();

	public Integer calculateDefaultWindowSize() {
		int gamesPlayed = winsAndLosses.size();
		int windowSize = (int) (0.3 * gamesPlayed);

		if (windowSize < 3) {
			windowSize = 3;
		} else if (windowSize > 100) {
			windowSize = 100;
		}

		return windowSize;
	}

	private double calculateWinRate(int windowSize, int i) {
		List<Boolean> window = getWindow(winsAndLosses, windowSize, i);
		long wins = window.stream().filter(b -> b).count();
		return window.isEmpty() ? 0.0 : ((double) wins) / window.size();
	}

	public List<Double> calculateWinRateRollingAverage(int windowSize) {
		List<Double> rollingWinRates = new ArrayList<>();
		for (int i = 0; i < winsAndLosses.size(); i++) {
			rollingWinRates.add(calculateWinRate(windowSize, i));
		}

		return rollingWinRates;
	}

	// private boolean[] generateData(int numberOfDataPoints, int windowSize) {
	// boolean[] winsAndLosses = new boolean[numberOfDataPoints];
	//
	// // Insert data slightly randomized to follow a curve
	// double minWinRate = 0.1;
	// double maxWinRate = 0.9;
	// double slope = (maxWinRate - minWinRate) / winsAndLosses.length;
	// double slopeIntercept = minWinRate;
	// for (int i = 0; i < winsAndLosses.length; i++) {
	// double targetWinRate = slope * i + slopeIntercept;
	// boolean isWin = random.nextDouble() < targetWinRate;
	// winsAndLosses[i] = isWin;
	// }
	//
	// return winsAndLosses;
	// }

	private List<Boolean> getWindow(List<Boolean> items, int windowSize, int index) {
		List<Boolean> window = new ArrayList<>();
		for (int i = index - windowSize + 1; i <= index; i++) {
			if (i >= 0 && i < items.size()) {
				window.add(items.get(i));
			}
		}
		return window;
	}

	public void onGameFinished(IGame game) {
		if (game.getGameResult() instanceof WinnerResult) {
			WinnerResult result = (WinnerResult) game.getGameResult();
			winsAndLosses.add(result.getWinningPlayer().equals("O"));
		}
	}
}
