angular.module('kettleApp',[])
    .controller('kettleController', function($scope, $http) {
	$scope.power = "off";

	$scope.getNumber = function(num) {
	    var arr = [];
	    for(var i=0; i<num; i++) {
		if(i < 10)
		    arr.push("0"+i);
		else
		    arr.push(i);
	    }
	    return arr;
	}

	$scope.turnOn = function() {
	    $scope.power = "on";
	    $http.get('/on');
	}

	$scope.turnOff = function() {
	    $scope.power = "off";
	    $http.get('/off');
	}
    });