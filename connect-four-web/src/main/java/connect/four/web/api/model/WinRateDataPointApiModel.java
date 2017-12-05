package connect.four.web.api.model;

public class WinRateDataPointApiModel {

	private double winRate;

	public WinRateDataPointApiModel(double winRate) {
		this.winRate = winRate;
	}

	public double getWinRate() {
		return winRate;
	}
}
