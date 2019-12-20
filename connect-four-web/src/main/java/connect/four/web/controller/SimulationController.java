package connect.four.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import connect.four.bot.BasicLogicBot;
import connect.four.bot.DumbRandomBot;
import connect.four.bot.IConnectFourBot;
import connect.four.bot.MachineLearningBot;
import connect.four.core.exception.ActionNotAllowedException;
import connect.four.web.api.model.BotType;
import connect.four.web.api.model.SimulationParametersApiModel;
import connect.four.web.service.MachineLearningService;
import connect.four.web.service.SimulationService;

@RestController
@RequestMapping("/api/simulation")
public class SimulationController {

	@Autowired
	private SimulationService simulationService;

	@Autowired
	private MachineLearningService machineLearningService;

	private IConnectFourBot buildBot(BotType botType, String playerToken, String opponentToken) {
		switch (botType) {
		case DUMB_RANDOM:
			return new DumbRandomBot();
		case BASIC_LOGIC:
			return new BasicLogicBot(playerToken, opponentToken);
		case MACHINE_LEARNING:
			return new MachineLearningBot(playerToken, machineLearningService.getNeuralNetwork());
		default:
			throw new RuntimeException("BotType not handled: " + botType);
		}
	}

	@RequestMapping(value = "/simulate", method = RequestMethod.POST)
	public void simulateGames(@RequestBody SimulationParametersApiModel simulationParameters)
			throws ActionNotAllowedException {
		IConnectFourBot playerX = buildBot(simulationParameters.getBotX(), "X", "O");
		IConnectFourBot playerO = buildBot(simulationParameters.getBotO(), "O", "X");
		simulationService.simulate(playerX, playerO, simulationParameters.getNumberOfGames(),
				simulationParameters.getBucketSize());
	}
}
