package connect.four.web.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connect.four.core.GameFactory;
import connect.four.core.GameProperties;
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

	private int nextId = 1;
	private Map<Integer, IGame> gameMap = new HashMap<>();
	private Random random = new Random();

	public GameApiModel createGame() {
		int id = nextId++;
		IGame game = GameFactory.createGame(getWebPlayer(), getAiPlayer());
		gameMap.put(id, game);

		// TODO: Change the game so it randomizes the first player and provides an API
		// method to get the current player.

		// Make the first move if AI is first
		if (game.getCurrentPlayer().equals(getAiPlayer())) {
			makeAiDrop(game, getAiPlayer());
		}

		return toApiModel(id, game);
	}

	public GameApiModel dropChecker(int id, int col) throws ActionNotAllowedException, InvalidGridLocationException {
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

	private void makeAiDrop(IGame game, String aiPlayer) {
		while (true) {
			int col = random.nextInt(GameProperties.COLS);
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
		return apiModel;
	}

}
