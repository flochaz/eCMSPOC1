'use strict';

angular.module('ecmspoc1App').controller('AirlineDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Airline', 'Catalog',
        function($scope, $stateParams, $uibModalInstance, entity, Airline, Catalog) {

        $scope.airline = entity;
        $scope.catalogs = Catalog.query();
        $scope.load = function(id) {
            Airline.get({id : id}, function(result) {
                $scope.airline = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ecmspoc1App:airlineUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.airline.id != null) {
                Airline.update($scope.airline, onSaveSuccess, onSaveError);
            } else {
                Airline.save($scope.airline, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
