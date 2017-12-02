angular.module('app').controller('homeController', ['$scope', '$location', function($scope, $location){
	$scope.playNewGame = function(){
		$location.path("/play");
	};
}]);