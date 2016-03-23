'use strict';

angular.module('ecmspoc1App')
    .factory('DeploymentGroup', function ($resource, DateUtils) {
        return $resource('api/deploymentGroups/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
