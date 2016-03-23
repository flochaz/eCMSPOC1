'use strict';

angular.module('ecmspoc1App').controller('TitleDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Title',
        function($scope, $stateParams, $uibModalInstance, entity, Title) {

        $scope.title = entity;
        $scope.load = function(id) {
            Title.get({id : id}, function(result) {
                $scope.title = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ecmspoc1App:titleUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.title.id != null) {
                Title.update($scope.title, onSaveSuccess, onSaveError);
            } else {
                Title.save($scope.title, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForExhibitionStartDate = {};

        $scope.datePickerForExhibitionStartDate.status = {
            opened: false
        };

        $scope.datePickerForExhibitionStartDateOpen = function($event) {
            $scope.datePickerForExhibitionStartDate.status.opened = true;
        };
        $scope.datePickerForExhibitionEndDate = {};

        $scope.datePickerForExhibitionEndDate.status = {
            opened: false
        };

        $scope.datePickerForExhibitionEndDateOpen = function($event) {
            $scope.datePickerForExhibitionEndDate.status.opened = true;
        };
        $scope.datePickerForCreationDate = {};

        $scope.datePickerForCreationDate.status = {
            opened: false
        };

        $scope.datePickerForCreationDateOpen = function($event) {
            $scope.datePickerForCreationDate.status.opened = true;
        };
        $scope.datePickerForLatestModifiedDate = {};

        $scope.datePickerForLatestModifiedDate.status = {
            opened: false
        };

        $scope.datePickerForLatestModifiedDateOpen = function($event) {
            $scope.datePickerForLatestModifiedDate.status.opened = true;
        };
}]);
