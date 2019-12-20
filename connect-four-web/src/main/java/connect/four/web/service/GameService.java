package connect.four.web.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connect.four.bot.IConnectFourBot;
import connect.four.bot.MachineLearningBot;
import connect.four.core.GameFactory;
import connect.four.core.GameStatus;
import connect.four.core.IGame;
import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.web.api.model.GameActionApiModel;
import connect.four.web.api.model.GameApiModel;
import connect.four.web.api.model.GameGridApiModel;
import connect.four.web.api.model.GameResultApiModel;

@Service
public class GameService {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private MachineLearningService machineLearningService;

	private IConnectFourBot robotPlayer;

	private int nextId = 1;
	private Map<Integer, IGame> gameMap = new HashMap<>();

	public GameApiModel createGame() {
		int id = nextId++;
		IGame game = GameFactory.createGame(getWebPlayer(), getAiPlayer());
		gameMap.put(id, game);

		// Make the first move if AI is first
		if (game.getCurrentPlayer().equals(getAiPlayer())) {
			makeAiDrop(game, getAiPlayer());
		}

		return toApiModel(id, game);
	}

	public GameApiModel dropChecker(int id, int col) throws ActionNotAllowedException {
		IGame game = gameMap.get(id);
		if (game == null) {
			throw new IllegalArgumentException("Game " + id + " does not exist.");
		}

		// Drop for the web player
		String webPlayer = getWebPlayer();
		game.dropChecker(webPlayer, col);

		// Drop for the AI player
		if (game.getStatus() != GameStatus.COMPLETED) {
			String aiPlayer = getAiPlayer();
			makeAiDrop(game, aiPlayer);
		}

		// Is the game over?
		if (game.getStatus() == GameStatus.COMPLETED) {
			statisticsService.onGameFinished(game);
		}

		return toApiModel(id, game);
	}

	private String getAiPlayer() {
		return "O";
	}

	public GameApiModel getGame(int id) {
		GameApiModel apiModel = null;
		IGame game = gameMap.get(id);
		if (game != null) {
			apiModel = toApiModel(id, game);
		}
		return apiModel;
	}

	public Collection<GameApiModel> getGames() {
		return gameMap.entrySet().stream().map(entry -> toApiModel(entry.getKey(), entry.getValue()))
				.collect(Collectors.toList());
	}

	private String getWebPlayer() {
		return "X";
	}

	@PostConstruct
	public void init() {
		robotPlayer = new MachineLearningBot(getAiPlayer(), machineLearningService.getNeuralNetwork());
	}

	private void makeAiDrop(IGame game, String aiPlayer) {
		while (true) {
			int col = robotPlayer.makeDecision(game);
			try {
				game.dropChecker(aiPlayer, col);
			} catch (ActionNotAllowedException | InvalidGridLocationException e) {
				// Column is no good
				continue;
			}

			// If we made it here, it was dropped successfully
			break;
		}
	}

	private GameApiModel toApiModel(int id, IGame game) {
		GameApiModel apiModel = new GameApiModel();
		apiModel.setId(id);
		apiModel.setGameStatus(game.getStatus().toString());
		apiModel.setGameGrid(new GameGridApiModel(game));
		apiModel.setGameResult(new GameResultApiModel(game.getGameResult()));
		apiModel.setActions(
				game.getActions().stream().map(action -> new GameActionApiModel(action)).collect(Collectors.toList()));
		apiModel.setPlayers(game.getPlayers());
		apiModel.setCurrentPlayer(game.getCurrentPlayer());
		return apiModel;
	}

}
