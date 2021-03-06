'use strict';

angular.module('ecmspoc1App')
	.controller('CatalogDeleteController', function($scope, $uibModalInstance, entity, Catalog) {

        $scope.catalog = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Catalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
