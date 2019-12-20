package connect.four.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import connect.four.bot.IConnectFourBot;
import connect.four.bot.simulator.BotSimulator;
import connect.four.core.exception.ActionNotAllowedException;

@Service
public class SimulationService {

	@Autowired
	private StatisticsService statisticsService;

	@Autowired
	private MachineLearningService machineLearningService;

	public void simulate(IConnectFourBot playerX, IConnectFourBot playerO, int numberOfGames, int trainingBucketSize)
			throws ActionNotAllowedException {
		BotSimulator simulator = new BotSimulator(playerX, playerO);

		// Simulate in buckets and only train after each bucket
		int gamesLeftToSimulate = numberOfGames;

		while (gamesLeftToSimulate > 0) {
			int gamesInBucket = gamesLeftToSimulate > trainingBucketSize ? trainingBucketSize : gamesLeftToSimulate;

			System.out.println("Simulating bucket. Games left: " + gamesLeftToSimulate);
			simulator.simulateSeries(gamesInBucket, game -> {
				// Tell the statistics service
				statisticsService.onGameFinished(game);

				// Add training data to neural network
				machineLearningService.onGameFinished(game);
			});

			// Train the neural network
			System.out.println("Training network...");
			machineLearningService.train();

			gamesLeftToSimulate -= gamesInBucket;
		}
	}
}
