angular.module('app').controller('homeController', ['$scope', '$location', function($scope, $location){
	$scope.message = "This is the message from the controller.";
	
	$scope.playNewGame = function(){
		$location.path("/play");
	};
}]);