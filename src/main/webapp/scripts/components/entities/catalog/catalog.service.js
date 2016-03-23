'use strict';

angular.module('ecmspoc1App')
    .factory('Catalog', function ($resource, DateUtils) {
        return $resource('api/catalogs/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.lastModified = DateUtils.convertLocaleDateFromServer(data.lastModified);
                    data.startDate = DateUtils.convertLocaleDateFromServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateFromServer(data.endDate);
                    data.catalogShipmentDeadline = DateUtils.convertLocaleDateFromServer(data.catalogShipmentDeadline);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.lastModified = DateUtils.convertLocaleDateToServer(data.lastModified);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.catalogShipmentDeadline = DateUtils.convertLocaleDateToServer(data.catalogShipmentDeadline);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.lastModified = DateUtils.convertLocaleDateToServer(data.lastModified);
                    data.startDate = DateUtils.convertLocaleDateToServer(data.startDate);
                    data.endDate = DateUtils.convertLocaleDateToServer(data.endDate);
                    data.catalogShipmentDeadline = DateUtils.convertLocaleDateToServer(data.catalogShipmentDeadline);
                    return angular.toJson(data);
                }
            }
        });
    });
