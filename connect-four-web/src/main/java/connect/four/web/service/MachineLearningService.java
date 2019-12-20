package connect.four.web.service;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import connect.four.bot.ConnectFourNeuralNetwork;
import connect.four.bot.ConnectFourNeuralNetworkInputData;
import connect.four.bot.ConnectFourNeuralNetworkOutputData;
import connect.four.core.GameGrid;
import connect.four.core.IGame;

@Service
public class MachineLearningService {

	private ConnectFourNeuralNetwork neuralNetwork;

	public ConnectFourNeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	@PostConstruct
	public void init() {
		neuralNetwork = new ConnectFourNeuralNetwork();
	}

	public void onGameFinished(IGame game) {
		// Add training data to the neural network:
		// - each game grid in the game history from the perspective of each player
		for (String playerToken : game.getPlayers()) {
			ConnectFourNeuralNetworkOutputData outputData = new ConnectFourNeuralNetworkOutputData(game.getGameResult(),
					playerToken);
			for (GameGrid grid : game.getGameGridHistory()) {
				ConnectFourNeuralNetworkInputData inputData = new ConnectFourNeuralNetworkInputData(grid, playerToken);
				neuralNetwork.addTrainingData(inputData, outputData);
			}
		}

		// Traing the network
		// neuralNetwork.train();
	}

	public void train() {
		neuralNetwork.train();
	}
}
