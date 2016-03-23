'use strict';

angular.module('ecmspoc1App')
    .controller('ContentDefinitionDetailController', function ($scope, $rootScope, $stateParams, entity, ContentDefinition, DeploymentGroup) {
        $scope.contentDefinition = entity;
        $scope.load = function (id) {
            ContentDefinition.get({id: id}, function(result) {
                $scope.contentDefinition = result;
            });
        };
        var unsubscribe = $rootScope.$on('ecmspoc1App:contentDefinitionUpdate', function(event, result) {
            $scope.contentDefinition = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
