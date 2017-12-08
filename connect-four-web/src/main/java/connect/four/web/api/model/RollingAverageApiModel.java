package connect.four.web.api.model;

import java.util.List;
import java.util.stream.Collectors;

public class RollingAverageApiModel {

	private int windowSize;
	private List<Double> winRates;
	private List<Double> lossRates;
	private List<Double> tieRates;

	public RollingAverageApiModel(int windowSize, List<MatchResultRates> matchResultRates) {
		this.windowSize = windowSize;
		this.winRates = matchResultRates.stream().map(rates -> rates.getWinRate()).collect(Collectors.toList());
		this.lossRates = matchResultRates.stream().map(rates -> rates.getLossRate()).collect(Collectors.toList());
		this.tieRates = matchResultRates.stream().map(rates -> rates.getTieRate()).collect(Collectors.toList());
	}

	public List<Double> getLossRates() {
		return lossRates;
	}

	public List<Double> getTieRates() {
		return tieRates;
	}

	public int getWindowSize() {
		return windowSize;
	}

	public List<Double> getWinRates() {
		return winRates;
	}
}
