package connect.four.bot;

import java.util.Arrays;

import connect.four.core.GameGrid;
import connect.four.core.GameProperties;
import connect.four.core.exception.InvalidGridLocationException;

public class ConnectFourNeuralNetworkInputData {
	private static final double UNOCCUPIED = 0.0;
	private static final double OCCUPIED_BY_ME = -1.0;
	private static final double OCCUPIED_BY_OPPONENT = 1.0;

	private double[] data;

	private ConnectFourNeuralNetworkInputData(double[] data) {
		this.data = data;
	}

	public ConnectFourNeuralNetworkInputData(GameGrid gameGrid, String myToken) throws InvalidGridLocationException {
		data = new double[GameProperties.ROWS * GameProperties.COLS];
		for (int row = 0; row < GameProperties.ROWS; row++) {
			for (int col = 0; col < GameProperties.COLS; col++) {
				int i = row * GameProperties.COLS + col;
				String occupyingPlayer = gameGrid.getChecker(row, col);
				if (occupyingPlayer == null) {
					data[i] = UNOCCUPIED;
				} else if (occupyingPlayer.equals(myToken)) {
					data[i] = OCCUPIED_BY_ME;
				} else {
					data[i] = OCCUPIED_BY_OPPONENT;
				}
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
		ConnectFourNeuralNetworkInputData other = (ConnectFourNeuralNetworkInputData) obj;
		if (!Arrays.equals(data, other.data))
			return false;
		return true;
	}

	public double[] getData() {
		return data;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(data);
		return result;
	}
}
