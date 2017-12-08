angular.module('app').controller('homeController', ['$scope', '$location', 'statisticsApi', function($scope, $location, statisticsApi){

	$scope.playNewGame = function(){
		$location.path("/play");
	};
	
	var createRollingWinRateChart = function(){
		statisticsApi.getWinRateRollingAverage().then(function(apiModel){
			$scope.windowSize = apiModel.windowSize;
			
			var data = [];
			var labels = [];
			for(var i = 0; i < apiModel.winRates.length; i++) {
				labels.push(i + 1);
			}
			
			var ctx = document.getElementById('rolling-win-rate-chart').getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'line',
			    data: {
				    labels: labels,
			    	datasets: [
			    		{
				    		label: 'Win Rate',
				    		borderColor: '#0F0',
				    		data: apiModel.winRates,
				    		fill: false
				    	},
			    		{
				    		label: 'Loss Rate',
				    		borderColor: '#F00',
				    		data: apiModel.lossRates,
				    		fill: false
				    	},
			    		{
				    		label: 'Tie Rate',
				    		borderColor: '#00F',
				    		data: apiModel.tieRates,
				    		fill: false
				    	}
			    	],
			    },
			    options: {
			    	responsive: false,
			    	scales: {
			    		yAxes: [{
			    			display: true,
			    			ticks: {
			    				suggestedMin: 0,
			    				suggestedMax: 1
			    			}
			    		}]
			    	},
			    	elements: {
			    		point: {
			    			radius: 0
			    		}
			    	}
			    }
			});
			
		});
	};
	
	// Load up the chart
	createRollingWinRateChart();
}]);