package connect.four.bot;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import connect.four.core.GameGrid;
import connect.four.core.IGame;
import connect.four.core.exception.InvalidGridLocationException;

public class MachineLearningBot implements IConnectFourBot {

	private String myToken;
	private Random random = new Random();
	private ConnectFourNeuralNetwork network;
	private double randomizationRate; // How often it will apply randomization

	public MachineLearningBot(String myToken, ConnectFourNeuralNetwork network) {
		this.myToken = myToken;
		this.network = network;
		this.randomizationRate = 0.5;
	}

	@Override
	public int makeDecision(IGame game) {
		try {
			List<Integer> availableColumns = game.getAvailableColumns();
			if (availableColumns.size() == 1) {
				return availableColumns.get(0);
			}

			// Compute from the network for each available column
			Map<Integer, Double> columnProbabilityMap = new HashMap<>();
			for (int i = 0; i < availableColumns.size(); i++) {
				// Build the grid for the potential drop
				GameGrid potentialGrid = game.getGrid().clone();
				potentialGrid.dropChecker(availableColumns.get(i), myToken);

				// Calculate against the network
				ConnectFourNeuralNetworkInputData inputData = new ConnectFourNeuralNetworkInputData(potentialGrid,
						myToken);
				ConnectFourNeuralNetworkOutputData outputData = network.compute(inputData);

				// Store the output for comparison
				double successProbability = outputData.getSuccessProbability();
				columnProbabilityMap.put(availableColumns.get(i), successProbability);
			}

			return makeDecision(columnProbabilityMap);
		} catch (InvalidGridLocationException ex) {
			throw new RuntimeException(ex);
		}
	}

	private int makeDecision(Map<Integer, Double> columnProbabilityMap) {
		int decision;

		if (random.nextDouble() < randomizationRate) {
			decision = makeDecisionWithRandomization(columnProbabilityMap);
		} else {
			// Take the key of the max value
			try {
				decision = columnProbabilityMap.entrySet().stream().max(Comparator.comparing(Entry::getValue)).get()
						.getKey();
			} catch (Throwable e) {
				throw e;
			}
		}

		return decision;
	}

	private int makeDecisionWithRandomization(Map<Integer, Double> columnProbabilityMap) {
		// Normalize (meaning that the sum of all probabilities should equal 1.0
		double sum = columnProbabilityMap.values().stream().collect(Collectors.summingDouble(Double::doubleValue));
		List<Entry<Integer, Double>> columnProbabilities = columnProbabilityMap.entrySet().stream()
				.map(e -> new AbstractMap.SimpleEntry<Integer, Double>(e.getKey(), e.getValue() / sum))
				.sorted(Comparator.comparing(Entry::getValue)).collect(Collectors.toList());

		// Apply randomization
		int decision = 0;
		double randomNumber = random.nextDouble();
		double cumulativeProbability = 0.0;
		for (Entry<Integer, Double> entry : columnProbabilities) {
			cumulativeProbability += entry.getValue();
			if (randomNumber <= cumulativeProbability) {
				decision = entry.getKey();
				break;
			}
		}
		return decision;
	}

}
