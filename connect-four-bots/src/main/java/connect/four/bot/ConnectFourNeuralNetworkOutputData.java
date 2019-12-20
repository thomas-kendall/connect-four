package connect.four.bot;

import java.util.Arrays;

import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;
import connect.four.core.result.WinnerResult;

public class ConnectFourNeuralNetworkOutputData {
	private static final int WIN = 0;
	private static final int LOSS = 1;
	private static final int TIE = 2;

	private double[] data;

	public ConnectFourNeuralNetworkOutputData(double[] data) {
		this.data = data.clone();
	}

	public ConnectFourNeuralNetworkOutputData(IGameResult gameResult, String me) {
		data = new double[3];
		if (gameResult instanceof NoWinnerResult) {
			data[TIE] = 1.0;
		} else if (gameResult instanceof WinnerResult) {
			if (((WinnerResult) gameResult).getWinningPlayer().equals(me)) {
				data[WIN] = 1.0;
			} else {
				data[LOSS] = 1.0;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectFourNeuralNetworkOutputData other = (ConnectFourNeuralNetworkOutputData) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	public double[] getData() {
		return data;
	}

	public double getSuccessProbability() {
		return -2.0 * data[LOSS] + data[TIE] + 2.0 * data[WIN];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}
}
