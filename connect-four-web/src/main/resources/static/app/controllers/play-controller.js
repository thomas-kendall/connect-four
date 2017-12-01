angular.module('app').controller('playController', ['$scope', '$location', '$timeout', 'gameService', function($scope, $location, $timeout, gameService){	
	$scope.message = "Good luck, sucka!";
	$scope.game = null;
	var svg = null;
	var width = 700;
	var height = 600;
	var rows = 6;
	var cols = 7;
	var checkerContainerMargin = 16;
	var squareWidth = width/cols;
	var squareHeight = height/rows;
	var checkerContainerRadius = squareWidth/2 - checkerContainerMargin/2;
	var checkerRadius = checkerContainerRadius - 2;
	var checkersDrawn;

	var startNewGame = function(){
		gameService.createGame().then(function(game){
			$scope.game = game;
			checkersDrawn = 0;
			drawGameGrid();
			drawNextDroppedChecker();
		});		
	};
	
	var drawNextDroppedChecker = function() {
		if(checkersDrawn < $scope.game.actions.length){
			var playerAction = $scope.game.actions[checkersDrawn];
			drawCheckerDrop(playerAction.player === 'X', playerAction.col, playerAction.row);
			checkersDrawn++;
			
			if(checkersDrawn < $scope.game.actions.length){
				$timeout(drawNextDroppedChecker, 1000);
			}
		}
	};
	
	var drawGameGrid = function(){		
		svg = d3.select('#game').append('svg').attr('width', width).attr('height', height).append('g');
		
		// Draw container
        svg.append('rect').attr('x', 0).attr('y', 0).attr('width', width).attr('height', height).style('fill', '#2980b9');
        
        // Draw checker containers (row=0 means top row)
        for(var row = 0; row < rows; row++){
        	var cy = getCenterY(row);
        	for(var col = 0; col < cols; col++){
        		var cx = squareWidth * col + squareWidth / 2;
        		svg.append('circle').attr('cx', cx).attr('cy', cy).attr('r', checkerContainerRadius).style('fill', '#FFF');
        	}
        }
	};
	
	// Reverses the y-coordinate for us
	var getCenterY = function(row){
		return height - squareHeight / 2 - squareHeight * row;
	};
	
	var drawCheckerDrop = function(isHuman, col, row){
		var checkerColor = isHuman ? '#F00' : '#FF0';
		var cx = squareWidth * col + squareWidth / 2;
		var cy = getCenterY(row);
		var checkerClass = 'checker-' + row + '-' + col;
		svg.append('circle').attr('cx', cx).attr('cy', -100).attr('r', checkerRadius).attr('class', checkerClass).style('fill', checkerColor);
		var t = d3.transition().duration(700).ease(d3.easeElastic);
		d3.select('circle.' + checkerClass).transition(t).attr('cy', cy);
	};
	
	// Create the game
	startNewGame();
	
//	gameService.getGames().then(function(games){
//		$scope.message = angular.toJson(games);
//	});	
	
	$scope.dropRandomly = function(){
		while(true){
			var col = Math.floor(Math.random() * cols);
			if($scope.game.gameGrid.rows[rows-1][col] !== null) continue;
			gameService.dropChecker($scope.game, col).then(function(game){
				$scope.message = angular.toJson(game);
				$scope.game = game;
				drawNextDroppedChecker();
			});
			break;
		}
	};
	
}]);