'use strict';

angular.module('ecmspoc1App')
    .factory('Title', function ($resource, DateUtils) {
        return $resource('api/titles/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.exhibitionStartDate = DateUtils.convertLocaleDateFromServer(data.exhibitionStartDate);
                    data.exhibitionEndDate = DateUtils.convertLocaleDateFromServer(data.exhibitionEndDate);
                    data.creationDate = DateUtils.convertLocaleDateFromServer(data.creationDate);
                    data.latestModifiedDate = DateUtils.convertLocaleDateFromServer(data.latestModifiedDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.exhibitionStartDate = DateUtils.convertLocaleDateToServer(data.exhibitionStartDate);
                    data.exhibitionEndDate = DateUtils.convertLocaleDateToServer(data.exhibitionEndDate);
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.latestModifiedDate = DateUtils.convertLocaleDateToServer(data.latestModifiedDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.exhibitionStartDate = DateUtils.convertLocaleDateToServer(data.exhibitionStartDate);
                    data.exhibitionEndDate = DateUtils.convertLocaleDateToServer(data.exhibitionEndDate);
                    data.creationDate = DateUtils.convertLocaleDateToServer(data.creationDate);
                    data.latestModifiedDate = DateUtils.convertLocaleDateToServer(data.latestModifiedDate);
                    return angular.toJson(data);
                }
            }
        });
    });
