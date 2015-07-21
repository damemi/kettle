angular.module('kettleApp',[])
    .controller('kettleController', function($scope, $http) {
	$scope.power = "off";
	$scope.hour = "Hour";
	$scope.minute = "Minute";
	$scope.ampm = "AM";

	$scope.checkAlarm = [];
	$http.get('/checkAlarm').then(function(response) {
	    $scope.checkAlarm.status = response.data.response;
	    
	    if(response.data.response == "True") {
		if(response.data.hour > 12)
		    $scope.hour = response.data.hour - 12
		else
		    $scope.hour = response.data.hour

		$scope.ampm = response.data.ampm

		if(response.data.minute < 10)
		    $scope.minute = "0"+response.data.minute
		else
		    $scope.minute = response.data.minute
	    }

	});

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

	$scope.setAlarm = function() {
	    $http.get('/setAlarm/'+$scope.hour+'/'+$scope.minute+'/'+$scope.ampm)
		.success(function(data) {
		    $scope.checkAlarm.status = "True";
		}).error(function(data) {
		    console.log(data);
		})
	}

	$scope.stopAlarm = function() {
	    $http.get('/stopAlarm')
		.success(function(data) {
		    $scope.checkAlarm.status = "False";
		}).error(function(data) {
		    console.log(data);
		})
	}
    });