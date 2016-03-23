 'use strict';

angular.module('ecmspoc1App')
    .factory('notificationInterceptor', function ($q, AlertService) {
        return {
            response: function(response) {
                var alertKey = response.headers('X-ecmspoc1App-alert');
                if (angular.isString(alertKey)) {
                    AlertService.success(alertKey, { param : response.headers('X-ecmspoc1App-params')});
                }
                return response;
            }
        };
    });
