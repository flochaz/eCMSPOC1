'use strict';

angular.module('ecmspoc1App')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


