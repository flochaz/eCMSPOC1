'use strict';

angular.module('ecmspoc1App')
    .factory('ContentDefinition', function ($resource, DateUtils) {
        return $resource('api/contentDefinitions/:id', {}, {
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
