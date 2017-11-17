package connect.four.web.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import connect.four.core.GameFactory;
import connect.four.core.GameProperties;
import connect.four.core.GameStatus;
import connect.four.core.IGame;
import connect.four.core.IPlayer;
import connect.four.core.exception.ActionNotAllowedException;
import connect.four.core.exception.InvalidGridLocationException;
import connect.four.web.api.model.GameApiModel;
import connect.four.web.api.model.GameGridApiModel;
import connect.four.web.api.model.GameResultApiModel;
import connect.four.web.game.model.AiPlayer;
import connect.four.web.game.model.WebPlayer;

@Service
public class GameService {

	private int nextId = 1;
	private Map<Integer, IGame> gameMap = new HashMap<>();
	private Random random = new Random();

	public GameApiModel createGame(String playerName) {
		// TODO: Don't allow the same name as AI

		int id = nextId++;
		IPlayer player1 = new WebPlayer(playerName);
		IPlayer player2 = new AiPlayer("AI");
		if (random.nextBoolean()) {
			IPlayer p = player1;
			player1 = player2;
			player2 = p;
		}
		IGame game = GameFactory.createGame(player1, player2);
		gameMap.put(id, game);

		// Make the first move if AI is first
		if (player1 instanceof AiPlayer) {
			makeAiDrop(game, player1);
		}

		return toApiModel(id, game);
	}

	public GameApiModel dropChecker(int id, int col) throws ActionNotAllowedException, InvalidGridLocationException {
		IGame game = gameMap.get(id);
		if (game == null) {
			throw new IllegalArgumentException("Game " + id + " does not exist.");
		}

		// Drop for the web player
		WebPlayer webPlayer = getWebPlayer(game);
		game.dropChecker(webPlayer, col);

		// Drop for the AI player
		if (game.getStatus() != GameStatus.COMPLETED) {
			AiPlayer aiPlayer = getAiPlayer(game);
			makeAiDrop(game, aiPlayer);
		}

		return toApiModel(id, game);
	}

	private AiPlayer getAiPlayer(IGame game) {
		return (game.getPlayer1() instanceof AiPlayer) ? (AiPlayer) game.getPlayer1() : (AiPlayer) game.getPlayer2();
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

	private WebPlayer getWebPlayer(IGame game) {
		return (game.getPlayer1() instanceof WebPlayer) ? (WebPlayer) game.getPlayer1() : (WebPlayer) game.getPlayer2();
	}

	private void makeAiDrop(IGame game, IPlayer aiPlayer) {
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
		apiModel.setPlayer1(game.getPlayer1().getName());
		apiModel.setPlayer2(game.getPlayer2().getName());
		apiModel.setGameResult(new GameResultApiModel(game.getGameResult()));
		return apiModel;
	}

}
