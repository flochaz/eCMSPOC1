'use strict';

angular.module('ecmspoc1App')
    .controller('DeploymentGroupDetailController', function ($scope, $rootScope, $stateParams, entity, DeploymentGroup, Catalog, ContentDefinition) {
        $scope.deploymentGroup = entity;
        $scope.load = function (id) {
            DeploymentGroup.get({id: id}, function(result) {
                $scope.deploymentGroup = result;
            });
        };
        var unsubscribe = $rootScope.$on('ecmspoc1App:deploymentGroupUpdate', function(event, result) {
            $scope.deploymentGroup = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
