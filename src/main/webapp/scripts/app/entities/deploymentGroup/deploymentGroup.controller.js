'use strict';

angular.module('ecmspoc1App')
    .controller('DeploymentGroupController', function ($scope, $state, DeploymentGroup) {

        $scope.deploymentGroups = [];
        $scope.loadAll = function() {
            DeploymentGroup.query(function(result) {
               $scope.deploymentGroups = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.deploymentGroup = {
                name: null,
                packagingType: null,
                id: null
            };
        };
    });
