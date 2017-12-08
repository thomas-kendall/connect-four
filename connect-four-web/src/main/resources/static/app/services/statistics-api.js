angular.module('app').service('statisticsApi', ['$http', function($http){
	var config = {headers: {'Content-Type': 'application/json'}};
	
	this.getWinRateRollingAverage = function(windowSize){
		var windowSizeParam = '';
		if(typeof windowSize !== 'undefined' && windowSize !== null){
			windowSizeParam = '?windowSize=' + windowSize;
		}
		return $http.get('/api/statistics/rolling-average' + windowSizeParam, config)
		.then(function(response){
			return response.data;
		});
	};
}]);