package connect.four.web.api.model;

import java.util.List;

public class RollingAverageApiModel {

	private int windowSize;
	private List<WinRateDataPointApiModel> dataPoints;

	public RollingAverageApiModel(int windowSize, List<WinRateDataPointApiModel> dataPoints) {
		this.windowSize = windowSize;
		this.dataPoints = dataPoints;
	}

	public List<WinRateDataPointApiModel> getDataPoints() {
		return dataPoints;
	}

	public int getWindowSize() {
		return windowSize;
	}
}
