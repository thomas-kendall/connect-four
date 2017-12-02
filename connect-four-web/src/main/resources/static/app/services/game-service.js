angular.module('app').factory('gameService', ['$rootScope', '$timeout', 'gameApi', function($rootScope, $timeout, gameApi){
	var rows = 6;
	var cols = 7;
	var games = {};	
	
	var canDropChecker = function(id, col){
		var game = games[id];
		if(col < 0 || col >= cols) return false;
		if(game.apiModel.gameStatus !== 'IN_PROGRESS') return false;
		if(game.waitingForServer) return false;
		if(getCurrentPlayer(id) !== 'X') return false;
		if(game.apiModel.gameGrid.rows[rows-1][col] !== null) return false;
		return true;
	};	
	
	var createGame = function(controllerScope, onActionCallback){
		return gameApi.createGame().then(function(gameApiModel){
			var game = {
				apiModel: gameApiModel,
				waitingForServer: false,
				actionsProcessed: 0
			};
			games[gameApiModel.id] = game;
			
			// Wire up the callback
			var handler = $rootScope.$on(getActionEventId(gameApiModel.id), onActionCallback);
			controllerScope.$on('$destroy', handler);
			
			// Fire off any actions already done in the game
			processActions(gameApiModel.id);
			
			return gameApiModel.id;
		});
	};
	
	var processActions = function(id) {
		var game = games[id];
		if(game.actionsProcessed < game.apiModel.actions.length){
			var action = getAction(id, game.actionsProcessed);
			notifyActionEvent(id, action);
			game.actionsProcessed++;
			
			if(game.actionsProcessed < game.apiModel.actions.length){
				$timeout(processActions, 200, true, id);
			}
		}
	};
	
	var dropChecker = function(id, col){
		// TODO: create and return a promise so that we can call canDropChecker() here.
		var game = games[id];
		game.waitingForServer = true;
		return gameApi.dropChecker(id, col).then(function(gameApiModel){
			game.apiModel = gameApiModel;
			game.waitingForServer = false;
			processActions(id);				
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
	
	var getActionEventId = function(id) {
		return 'game-service-on-action-event-' + id;
	};
	
	var getCols = function() {
		return cols;
	};
	
	var getCurrentPlayer = function(id) {
		var game = games[id];
		if(game.apiModel.actions.length === 0) return 'X';
		if(game.waitingForServer) return 'O';
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
	
	var getStatus = function(id) {
		var status = "";
		var game = games[id];
		if(game.apiModel.gameStatus === 'IN_PROGRESS'){
			if(getCurrentPlayer(id) === 'X'){
				status = 'Your turn, play a checker.';
			} else {
				status = 'Waiting on AI...';
			}
		} else if(isGameOver(id)){
			var isWinner = game.apiModel.gameResult.winner === 'X';
			status = 'Game over. You ' + (isWinner ? 'win' : 'lose') + '!'; 
		}
		return status;
	};
	
	var isGameOver = function(id) {
		var game = games[id];		
		return game.apiModel.gameStatus === 'COMPLETED';
	}
	
	var notifyActionEvent = function(id, action) {
		$rootScope.$emit(getActionEventId(id), action);
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
		getStatus: getStatus,
		isGameOver: isGameOver,
	};
}]);