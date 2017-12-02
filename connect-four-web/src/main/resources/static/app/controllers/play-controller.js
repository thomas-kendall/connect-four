angular.module('app').controller('playController', ['$scope', '$location', '$timeout', 'gameService', function($scope, $location, $timeout, gameService){	
	$scope.message = "";
	$scope.gameId = null;
	var svg = null;
	var width = 700;
	var height = 600;
	var checkerContainerMargin = 16;
	var squareWidth = width/gameService.getCols();
	var squareHeight = height/gameService.getRows();
	var checkerContainerRadius = squareWidth/2 - checkerContainerMargin/2;
	var checkerRadius = checkerContainerRadius - 2;
	var checkersDrawn;
	var selectedCol = -1;

	var startNewGame = function(){
		gameService.createGame().then(function(gameId){
			$scope.gameId = gameId;
			checkersDrawn = 0;
			drawGameGrid();
			drawNextDroppedChecker();
		});		
	};
	
	var drawNextDroppedChecker = function() {
		var action = gameService.getAction($scope.gameId, checkersDrawn);
		if(action !== null){
			drawCheckerDrop(action.player === 'X', action.col, action.row);
			checkersDrawn++;
			$timeout(drawNextDroppedChecker, 500);
		}
	};
	
	var drawGameGrid = function(){		
		svg = d3.select('#game').append('svg').attr('width', width).attr('height', height).append('g');
		
		// Draw container
        svg.append('rect').attr('x', 0).attr('y', 0).attr('width', width).attr('height', height).style('fill', '#2980b9');
        
        // Draw checker containers (row=0 means bottom row)
        for(var row = 0; row < gameService.getRows(); row++){
        	var cy = getCenterY(row);
        	for(var col = 0; col < gameService.getCols(); col++){
        		var cx = getCenterX(col);
        		svg.append('circle').attr('cx', cx).attr('cy', cy).attr('r', checkerContainerRadius)
        		.style('fill', '#FFF')
        		.attr('class', 'container-row-' + row + ' container-col-' + col)
        		.attr('data-col', '' + col);
        	}
        }
        
        // Attach hover handlers for top row
        d3.selectAll('.container-row-' + (gameService.getRows() - 1))
        .on('mouseover', function(){
        	var containerCircle = d3.select(this); 
        	selectedCol = parseInt(containerCircle.attr('data-col'));
        	if(gameService.canDropChecker($scope.gameId, selectedCol)){
        		containerCircle.style('fill', '#F00');
        	}
        })
        .on('mouseout', function(){
        	selectedCol = -1;
        	d3.select(this).style('fill', '#FFF');
        })
        .on('click', function(){
        	if(gameService.canDropChecker($scope.gameId, selectedCol)){
        		dropChecker(selectedCol);
        	}
        });
	};
	
	var getCenterX = function(col) {
		return squareWidth * col + squareWidth / 2;
	};
	
	// Reverses the y-coordinate for us
	var getCenterY = function(row) {
		return height - squareHeight / 2 - squareHeight * row;
	};
	
	var drawCheckerDrop = function(isHuman, col, row){
		var checkerColor = isHuman ? '#F00' : '#FF0';
		var cx = getCenterX(col);
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
	
	var dropChecker = function(col){
		if(gameService.canDropChecker($scope.gameId, col)){
			gameService.dropChecker($scope.gameId, col).then(function(){
				$scope.message = gameService.getGameJson($scope.gameId); 
				drawNextDroppedChecker();
			});		
		}
	};
		
}]);