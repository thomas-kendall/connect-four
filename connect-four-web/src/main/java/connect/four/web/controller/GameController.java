package connect.four.web.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import connect.four.core.exception.ActionNotAllowedException;
import connect.four.web.api.model.DropCheckerRequestApiModel;
import connect.four.web.api.model.GameApiModel;
import connect.four.web.service.GameService;

@RestController
@RequestMapping("/api/games")
public class GameController {

	@Autowired
	private GameService gameService;

	@RequestMapping(method = RequestMethod.POST)
	public GameApiModel createGame() {
		GameApiModel apiModel = gameService.createGame();
		return apiModel;
	}

	@RequestMapping(value = "/{gameId}/checkers", method = RequestMethod.POST)
	public GameApiModel dropChecker(@PathVariable int gameId,
			@RequestBody DropCheckerRequestApiModel dropCheckerRequestApiModel) throws ActionNotAllowedException {
		GameApiModel apiModel = gameService.dropChecker(gameId, dropCheckerRequestApiModel.getCol());
		return apiModel;
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<GameApiModel> getGames() {
		Collection<GameApiModel> apiResult = gameService.getGames();
		return apiResult;
	}

}
