'use strict';

angular.module('ecmspoc1App')
    .controller('ContentDefinitionController', function ($scope, $state, ContentDefinition) {

        $scope.contentDefinitions = [];
        $scope.loadAll = function() {
            ContentDefinition.query(function(result) {
               $scope.contentDefinitions = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.contentDefinition = {
                name: null,
                version: null,
                id: null
            };
        };
    });
