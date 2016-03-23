'use strict';

angular.module('ecmspoc1App')
    .controller('TitleDetailController', function ($scope, $rootScope, $stateParams, entity, Title, Catalog, ContentDefinition) {
        $scope.title = entity;
        $scope.load = function (id) {
            Title.get({id: id}, function(result) {
                $scope.title = result;
            });
        };
        var unsubscribe = $rootScope.$on('ecmspoc1App:titleUpdate', function(event, result) {
            $scope.title = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
