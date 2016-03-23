'use strict';

angular.module('ecmspoc1App')
	.controller('AirlineDeleteController', function($scope, $uibModalInstance, entity, Airline) {

        $scope.airline = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Airline.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
