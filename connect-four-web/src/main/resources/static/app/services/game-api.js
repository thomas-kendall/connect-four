angular.module('app').service('gameApi', ['$http', function($http){
	var config = {headers: {'Content-Type': 'application/json'}};
	
	this.getGames = function(){
		return $http.get('/api/games', config)
		.then(function(response){
			return response.data;
		});
	};
	
	this.createGame = function(){
		return $http.post('/api/games', config)
		.then(function(response){
			return response.data;
		});
	};
	
	this.dropChecker = function(gameId, column){
		var data = {
			col: column
		};
		return $http.post('/api/games/' + gameId + '/checkers', data, config)
		.then(function(response){
			return response.data;
		});
	};
}]);