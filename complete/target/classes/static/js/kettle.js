angular.module('kettleApp',[])
    .controller('kettleController', function($scope, $http) {
	$scope.power = "off";

	$scope.turnOn = function() {
	    $scope.power = "on";
	    $http.get('/on');
	}

	$scope.turnOff = function() {
	    $scope.power = "off";
	    $http.get('/off');
	}
    });