'use strict';

angular.module('ecmspoc1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('airline', {
                parent: 'entity',
                url: '/airlines',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Airlines'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/airline/airlines.html',
                        controller: 'AirlineController'
                    }
                },
                resolve: {
                }
            })
            .state('airline.detail', {
                parent: 'entity',
                url: '/airline/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Airline'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/airline/airline-detail.html',
                        controller: 'AirlineDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Airline', function($stateParams, Airline) {
                        return Airline.get({id : $stateParams.id});
                    }]
                }
            })
            .state('airline.new', {
                parent: 'airline',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/airline/airline-dialog.html',
                        controller: 'AirlineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    icaoCode: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('airline', null, { reload: true });
                    }, function() {
                        $state.go('airline');
                    })
                }]
            })
            .state('airline.edit', {
                parent: 'airline',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/airline/airline-dialog.html',
                        controller: 'AirlineDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Airline', function(Airline) {
                                return Airline.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('airline', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('airline.delete', {
                parent: 'airline',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/airline/airline-delete-dialog.html',
                        controller: 'AirlineDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Airline', function(Airline) {
                                return Airline.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('airline', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
