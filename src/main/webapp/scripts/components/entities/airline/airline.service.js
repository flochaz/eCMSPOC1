'use strict';

angular.module('ecmspoc1App')
    .factory('Airline', function ($resource, DateUtils) {
        return $resource('api/airlines/:id', {}, {
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
