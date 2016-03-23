'use strict';

angular.module('ecmspoc1App')
    .controller('CatalogController', function ($scope, $state, Catalog) {

        $scope.catalogs = [];
        $scope.loadAll = function() {
            Catalog.query(function(result) {
               $scope.catalogs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.catalog = {
                name: null,
                status: null,
                lastModified: null,
                startDate: null,
                endDate: null,
                catalogShipmentDeadline: null,
                description: null,
                id: null
            };
        };
    });
