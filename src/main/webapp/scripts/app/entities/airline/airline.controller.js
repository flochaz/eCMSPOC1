'use strict';

angular.module('ecmspoc1App')
    .controller('AirlineController', function ($scope, $state, Airline) {

        $scope.airlines = [];
        $scope.loadAll = function() {
            Airline.query(function(result) {
               $scope.airlines = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.airline = {
                name: null,
                icaoCode: null,
                id: null
            };
        };
    });
