package connect.four.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import connect.four.core.IGame;
import connect.four.core.result.WinnerResult;
import connect.four.web.api.model.MatchResult;
import connect.four.web.api.model.MatchResultRates;

@Service
public class StatisticsService {
	private class LineSegmentDefinition {
		public double from;
		public double to;

		public LineSegmentDefinition(double from, double to) {
			this.from = from;
			this.to = to;
		}
	}

	private Random random = new Random();
	private List<MatchResult> matchResults = new ArrayList<>();

	private double[] buildCurve(LineSegmentDefinition[] curveDefinition, int dataPoints) {
		double[] result = new double[dataPoints];
		int index = 0;

		int pointsPerSegment = dataPoints / curveDefinition.length;
		for (LineSegmentDefinition lineSegment : curveDefinition) {
			double stepSize = (lineSegment.to - lineSegment.from) / pointsPerSegment;
			for (int i = index; i < pointsPerSegment; i++, index++) {
				result[i] = lineSegment.from + i * stepSize;
			}
		}
		// Fill any remaining points with previous point
		for (int i = index; i < result.length; i++, index++) {
			result[i] = result[i - 1];
		}
		return result;
	}

	public Integer calculateDefaultWindowSize() {
		int gamesPlayed = matchResults.size();
		int windowSize = (int) (0.3 * gamesPlayed);

		if (windowSize < 3) {
			windowSize = 3;
		} else if (windowSize > 100) {
			windowSize = 100;
		}

		return windowSize;
	}

	private double calculateRate(List<MatchResult> window, int i, MatchResult matchResult) {
		long wins = window.stream().filter(mr -> mr == matchResult).count();
		return window.isEmpty() ? 0.0 : ((double) wins) / window.size();
	}

	public List<MatchResultRates> calculateRatesRollingAverage(int windowSize) {
		List<MatchResultRates> rollingRates = new ArrayList<>();
		for (int i = 0; i < matchResults.size(); i++) {
			List<MatchResult> window = getWindow(matchResults, windowSize, i);
			MatchResultRates rates = new MatchResultRates();
			rates.setWinRate(calculateRate(window, i, MatchResult.WIN));
			rates.setLossRate(calculateRate(window, i, MatchResult.LOSS));
			rates.setTieRate(calculateRate(window, i, MatchResult.TIE));
			rollingRates.add(rates);
		}

		return rollingRates;
	}

	public void generateData(int numberOfDataPoints, int windowSize) {
		// Insert data slightly randomized to follow a curve
		LineSegmentDefinition[] winRatesCurveDefinition = new LineSegmentDefinition[] {
				new LineSegmentDefinition(0.1, 0.9) };
		double[] winRates = buildCurve(winRatesCurveDefinition, numberOfDataPoints);
		LineSegmentDefinition[] tieRatesCurveDefinition = new LineSegmentDefinition[] {
				new LineSegmentDefinition(0.1, 0.3), new LineSegmentDefinition(0.3, 0.1) };
		double[] tieRates = buildCurve(tieRatesCurveDefinition, numberOfDataPoints);

		for (int i = 0; i < numberOfDataPoints; i++) {
			matchResults.add(generateMatchResult(winRates[i], tieRates[i]));
		}
	}

	private MatchResult generateMatchResult(double winRate, double tieRate) {
		double d = random.nextDouble();
		return d < winRate ? MatchResult.WIN : d < winRate + tieRate ? MatchResult.TIE : MatchResult.LOSS;
	}

	private List<MatchResult> getWindow(List<MatchResult> items, int windowSize, int index) {
		List<MatchResult> window = new ArrayList<>();
		for (int i = index - windowSize + 1; i <= index; i++) {
			if (i >= 0 && i < items.size()) {
				window.add(items.get(i));
			}
		}
		return window;
	}

	public void onGameFinished(IGame game) {
		MatchResult matchResult;
		if (game.getGameResult() instanceof WinnerResult) {
			WinnerResult result = (WinnerResult) game.getGameResult();
			matchResult = result.getWinningPlayer().equals("O") ? MatchResult.WIN : MatchResult.LOSS;
		} else {
			matchResult = MatchResult.TIE;
		}
		matchResults.add(matchResult);
	}
}
