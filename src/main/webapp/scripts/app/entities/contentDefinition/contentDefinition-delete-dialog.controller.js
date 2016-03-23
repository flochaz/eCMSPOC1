'use strict';

angular.module('ecmspoc1App')
	.controller('ContentDefinitionDeleteController', function($scope, $uibModalInstance, entity, ContentDefinition) {

        $scope.contentDefinition = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ContentDefinition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
