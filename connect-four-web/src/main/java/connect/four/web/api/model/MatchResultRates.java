package connect.four.web.api.model;

public class MatchResultRates {

	private double winRate;
	private double lossRate;
	private double tieRate;

	public double getLossRate() {
		return lossRate;
	}

	public double getTieRate() {
		return tieRate;
	}

	public double getWinRate() {
		return winRate;
	}

	public void setLossRate(double lossRate) {
		this.lossRate = lossRate;
	}

	public void setTieRate(double tieRate) {
		this.tieRate = tieRate;
	}

	public void setWinRate(double winRate) {
		this.winRate = winRate;
	}
}
