'use strict';

angular.module('ecmspoc1App').controller('DeploymentGroupDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'DeploymentGroup',
        function($scope, $stateParams, $uibModalInstance, entity, DeploymentGroup) {

        $scope.deploymentGroup = entity;
        $scope.load = function(id) {
            DeploymentGroup.get({id : id}, function(result) {
                $scope.deploymentGroup = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ecmspoc1App:deploymentGroupUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.deploymentGroup.id != null) {
                DeploymentGroup.update($scope.deploymentGroup, onSaveSuccess, onSaveError);
            } else {
                DeploymentGroup.save($scope.deploymentGroup, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
