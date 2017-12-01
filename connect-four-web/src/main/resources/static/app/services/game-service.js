angular.module('app').service('gameService', ['$location', 'gameApi', function($location, gameApi){
	
	this.getGames = function(){
		return gameApi.getGames();
	};
	
	this.createGame = function(){
		return gameApi.createGame();
	};
	
	this.dropChecker = function(game, column){
		return gameApi.dropChecker(game.id, column);
	};	
	
	this.canDropChecker = function(game, column){
		// rows[0] is the top row
		return game.gameGrid.rows[0][column] === null;
	};	
}]);