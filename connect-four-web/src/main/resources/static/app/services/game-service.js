angular.module('app').factory('gameService', ['$location', 'gameApi', function($location, gameApi){
	var rows = 6;
	var cols = 7;
	var games = {};	
	
	var canDropChecker = function(id, col){
		var game = games[id];
		if(col < 0 || col >= cols) return false;
		if(game.apiModel.gameStatus !== 'IN_PROGRESS') return false;
		if(game.waitingOnServer) return false;
		if(getCurrentPlayer(id) !== 'X') return false;
		if(game.apiModel.gameGrid.rows[rows-1][col] !== null) return false;
		return true;
	};	
	
	var createGame = function(){
		return gameApi.createGame().then(function(gameApiModel){
			games[gameApiModel.id] = {
				apiModel: gameApiModel,
				waitingForServer: false
			};
			return gameApiModel.id;
		});
	};
	
	var dropChecker = function(id, col){
		// TODO: create and return a promise so that we can call canDropChecker() here.
		var game = games[id];
		game.waitingForServer = true;
		return gameApi.dropChecker(id, col).then(function(gameApiModel){
			game.apiModel = gameApiModel;
			game.waitingForServer = false;
		});
	};	
	
	var getAction = function(id, index) {
		var action = null;
		var game = games[id];
		if(index >= 0 && index < game.apiModel.actions.length) {
			action = game.apiModel.actions[index];
		}
		return action;
	};
	
	var getCols = function() {
		return cols;
	};
	
	var getCurrentPlayer = function(id) {
		var game = games[id];
		if(game.apiModel.actions.length === 0) return 'X';
		var lastAction = game.apiModel.actions[game.apiModel.actions.length - 1];
		return lastAction.player === 'X'? 'O' : 'X';
	};
	
	var getGameJson = function(id) {
		var game = games[id];
		return angular.toJson(game.apiModel);
	};
	
	var getGames = function(){
		return gameApi.getGames();
	};
	
	var getRows = function() {
		return rows;
	};
	
	return {
		canDropChecker: canDropChecker,
		createGame: createGame,
		dropChecker: dropChecker,
		getAction: getAction,
		getCols: getCols,
		getCurrentPlayer: getCurrentPlayer,
		getGameJson: getGameJson,
		getGames: getGames,
		getRows: getRows,
	};
}]);