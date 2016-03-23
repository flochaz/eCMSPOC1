'use strict';

angular.module('ecmspoc1App')
    .controller('TitleController', function ($scope, $state, Title) {

        $scope.titles = [];
        $scope.loadAll = function() {
            Title.query(function(result) {
               $scope.titles = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.title = {
                name: null,
                exhibitionStartDate: null,
                exhibitionEndDate: null,
                creationDate: null,
                latestModifiedDate: null,
                id: null
            };
        };
    });
