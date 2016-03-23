'use strict';

angular.module('ecmspoc1App').controller('CatalogDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Catalog', 'Airline', 'DeploymentGroup', 'Title',
        function($scope, $stateParams, $uibModalInstance, entity, Catalog, Airline, DeploymentGroup, Title) {

        $scope.catalog = entity;
        $scope.airlines = Airline.query();
        $scope.deploymentgroups = DeploymentGroup.query();
        $scope.titles = Title.query();
        $scope.load = function(id) {
            Catalog.get({id : id}, function(result) {
                $scope.catalog = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ecmspoc1App:catalogUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.catalog.id != null) {
                Catalog.update($scope.catalog, onSaveSuccess, onSaveError);
            } else {
                Catalog.save($scope.catalog, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForLastModified = {};

        $scope.datePickerForLastModified.status = {
            opened: false
        };

        $scope.datePickerForLastModifiedOpen = function($event) {
            $scope.datePickerForLastModified.status.opened = true;
        };
        $scope.datePickerForStartDate = {};

        $scope.datePickerForStartDate.status = {
            opened: false
        };

        $scope.datePickerForStartDateOpen = function($event) {
            $scope.datePickerForStartDate.status.opened = true;
        };
        $scope.datePickerForEndDate = {};

        $scope.datePickerForEndDate.status = {
            opened: false
        };

        $scope.datePickerForEndDateOpen = function($event) {
            $scope.datePickerForEndDate.status.opened = true;
        };
        $scope.datePickerForCatalogShipmentDeadline = {};

        $scope.datePickerForCatalogShipmentDeadline.status = {
            opened: false
        };

        $scope.datePickerForCatalogShipmentDeadlineOpen = function($event) {
            $scope.datePickerForCatalogShipmentDeadline.status.opened = true;
        };
}]);
