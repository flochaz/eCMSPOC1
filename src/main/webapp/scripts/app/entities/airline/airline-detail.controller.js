'use strict';

angular.module('ecmspoc1App')
    .controller('AirlineDetailController', function ($scope, $rootScope, $stateParams, entity, Airline, Catalog) {
        $scope.airline = entity;
        $scope.load = function (id) {
            Airline.get({id: id}, function(result) {
                $scope.airline = result;
            });
        };
        var unsubscribe = $rootScope.$on('ecmspoc1App:airlineUpdate', function(event, result) {
            $scope.airline = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
