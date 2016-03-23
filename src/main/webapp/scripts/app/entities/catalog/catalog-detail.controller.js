'use strict';

angular.module('ecmspoc1App')
    .controller('CatalogDetailController', function ($scope, $rootScope, $stateParams, entity, Catalog) {
        $scope.catalog = entity;
        $scope.load = function (id) {
            Catalog.get({id: id}, function(result) {
                $scope.catalog = result;
            });
        };
        var unsubscribe = $rootScope.$on('ecmspoc1App:catalogUpdate', function(event, result) {
            $scope.catalog = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
