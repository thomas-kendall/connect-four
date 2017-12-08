package connect.four.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import connect.four.web.api.model.MatchResultRates;
import connect.four.web.api.model.RollingAverageApiModel;
import connect.four.web.service.StatisticsService;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@RequestMapping(value = "/generate-data", method = RequestMethod.GET)
	public void generateData() {
		statisticsService.generateData(1000, 30);
	}

	@RequestMapping(value = "/rolling-average", method = RequestMethod.GET)
	public RollingAverageApiModel getRollingAverage(@RequestParam(required = false) Integer windowSize) {
		if (windowSize == null || windowSize < 1) {
			// use a default window size
			windowSize = statisticsService.calculateDefaultWindowSize();
		}
		List<MatchResultRates> rollingRates = statisticsService.calculateRatesRollingAverage(windowSize);

		RollingAverageApiModel result = new RollingAverageApiModel(windowSize, rollingRates);
		return result;
	}

}
