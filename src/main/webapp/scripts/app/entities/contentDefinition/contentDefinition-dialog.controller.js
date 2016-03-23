'use strict';

angular.module('ecmspoc1App').controller('ContentDefinitionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContentDefinition', 'DeploymentGroup',
        function($scope, $stateParams, $uibModalInstance, entity, ContentDefinition, DeploymentGroup) {

        $scope.contentDefinition = entity;
        $scope.deploymentgroups = DeploymentGroup.query();
        $scope.load = function(id) {
            ContentDefinition.get({id : id}, function(result) {
                $scope.contentDefinition = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ecmspoc1App:contentDefinitionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contentDefinition.id != null) {
                ContentDefinition.update($scope.contentDefinition, onSaveSuccess, onSaveError);
            } else {
                ContentDefinition.save($scope.contentDefinition, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
