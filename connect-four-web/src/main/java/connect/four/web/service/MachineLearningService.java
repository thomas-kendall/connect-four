package connect.four.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import connect.four.bot.BasicNetworkSerializer;
import connect.four.bot.ConnectFourNeuralNetwork;
import connect.four.core.GameGrid;
import connect.four.core.GameProperties;
import connect.four.core.IGame;
import connect.four.core.result.IGameResult;
import connect.four.core.result.NoWinnerResult;
import connect.four.core.result.WinnerResult;
import connect.four.web.entity.GameStateResult;
import connect.four.web.entity.NeuralNetwork;
import connect.four.web.repository.GameStateResultRepository;
import connect.four.web.repository.NeuralNetworkRepository;

@Service
public class MachineLearningService {

	private static String getGameState(GameGrid gameGrid, String myToken) {
		StringBuilder builder = new StringBuilder();
		for (int row = 0; row < GameProperties.ROWS; row++) {
			StringBuilder line = new StringBuilder();
			for (int col = 0; col < GameProperties.COLS; col++) {
				String occupyingPlayer = gameGrid.getChecker(row, col);
				if (occupyingPlayer == null) {
					line.append('*');
				} else if (occupyingPlayer.equals(myToken)) {
					line.append('X');
				} else {
					line.append('O');
				}
			}
			builder.append(line);
			builder.append("\r\n");
		}
		return builder.toString();
	}

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	private ConnectFourNeuralNetwork neuralNetwork;

	@Autowired
	private GameStateResultRepository gameStateResultRepository;

	@Autowired
	private NeuralNetworkRepository neuralNetworkRepository;

	public void addGameResults(IGame game) {
		// Get all records by ID
		List<String> gameStates = new ArrayList<>();
		for (String playerToken : game.getPlayers()) {
			for (GameGrid grid : game.getGameGridHistory()) {
				gameStates.add(getGameState(grid, playerToken));
			}
		}

		// Find ones that may already exist
		List<GameStateResult> gameStateResults = new ArrayList<>();
		gameStateResultRepository.findAllById(gameStates).forEach(gameStateResults::add);

		// Apply the result for each game grid in the game history from the perspective
		// of each player
		for (String playerToken : game.getPlayers()) {
			for (GameGrid grid : game.getGameGridHistory()) {
				String gameState = getGameState(grid, playerToken);

				// Find or create the record
				Optional<GameStateResult> optionalGameStateResult = gameStateResults.stream()
						.filter(gsr -> gsr.getGameState().equals(gameState)).findFirst();
				GameStateResult gameStateResult;
				if (optionalGameStateResult.isPresent()) {
					gameStateResult = optionalGameStateResult.get();
				} else {
					gameStateResult = new GameStateResult(gameState, 0, 0, 0);
					gameStateResults.add(gameStateResult);
				}

				// Apply the game result
				applyGameResult(gameStateResult, game.getGameResult(), playerToken);
			}
		}

		// Persist
		gameStateResultRepository.saveAll(gameStateResults);
	}

	private void applyGameResult(GameStateResult gameStateResult, IGameResult gameResult, String myToken) {
		if (gameResult instanceof NoWinnerResult) {
			gameStateResult.incrementTieCount();
		} else if (gameResult instanceof WinnerResult) {
			if (((WinnerResult) gameResult).getWinningPlayer().equals(myToken)) {
				gameStateResult.incrementWinCount();

			} else {
				gameStateResult.incrementLossCount();
			}
		}
	}

	public ConnectFourNeuralNetwork getNeuralNetwork() {
		return neuralNetwork;
	}

	@PostConstruct
	public void init() {
		neuralNetwork = new ConnectFourNeuralNetwork();

//		// Create the tables
//		CreateTableRequest createTableRequest;
//		// GameStateResult
//		createTableRequest = dynamoDBMapper.generateCreateTableRequest(GameStateResult.class);
//		createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//		amazonDynamoDB.createTable(createTableRequest);
//		// NeuralNetwork
//		createTableRequest = dynamoDBMapper.generateCreateTableRequest(NeuralNetwork.class);
//		createTableRequest.setProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
//		amazonDynamoDB.createTable(createTableRequest);

		NeuralNetwork nn = new NeuralNetwork();
		nn.setDefinition(BasicNetworkSerializer.serialize(neuralNetwork.getBasicNetwork()));
		neuralNetworkRepository.save(nn);
	}

	public void onGameFinished(IGame game) {
		// Add training data to the neural network
		addGameResults(game);
	}

	public void train() {
		neuralNetwork.train();
	}
}
