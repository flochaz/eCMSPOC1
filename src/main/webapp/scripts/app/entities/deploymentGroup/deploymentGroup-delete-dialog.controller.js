'use strict';

angular.module('ecmspoc1App')
	.controller('DeploymentGroupDeleteController', function($scope, $uibModalInstance, entity, DeploymentGroup) {

        $scope.deploymentGroup = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeploymentGroup.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
