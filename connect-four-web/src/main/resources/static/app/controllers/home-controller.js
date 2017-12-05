angular.module('app').controller('homeController', ['$scope', '$location', 'statisticsApi', function($scope, $location, statisticsApi){

	$scope.playNewGame = function(){
		$location.path("/play");
	};
	
	var createRollingWinRateChart = function(){
		statisticsApi.getWinRateRollingAverage().then(function(apiModel){
			$scope.windowSize = apiModel.windowSize;
			
			var data = [];
			var labels = [];
			for(var i = 0; i < apiModel.dataPoints.length; i++) {
				labels.push(i + 1);
				data.push({
					x: i + 1,
					y: apiModel.dataPoints[i].winRate
				});
			}
			
			var ctx = document.getElementById('rolling-win-rate-chart').getContext('2d');
			var myChart = new Chart(ctx, {
			    type: 'line',
			    data: {
				    labels: labels,
			    	datasets: [{
			    		label: 'Win Rate',
			    		borderColor: '#00F',
			    		data: data,
			    		backgroundColor: '#DDF'
			    	}],
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
			    	}
			    }
			});
			
		});
	};
	
	// Load up the chart
	createRollingWinRateChart();
}]);