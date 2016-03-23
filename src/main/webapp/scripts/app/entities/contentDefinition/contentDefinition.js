'use strict';

angular.module('ecmspoc1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contentDefinition', {
                parent: 'entity',
                url: '/contentDefinitions',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ContentDefinitions'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contentDefinition/contentDefinitions.html',
                        controller: 'ContentDefinitionController'
                    }
                },
                resolve: {
                }
            })
            .state('contentDefinition.detail', {
                parent: 'entity',
                url: '/contentDefinition/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ContentDefinition'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/contentDefinition/contentDefinition-detail.html',
                        controller: 'ContentDefinitionDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ContentDefinition', function($stateParams, ContentDefinition) {
                        return ContentDefinition.get({id : $stateParams.id});
                    }]
                }
            })
            .state('contentDefinition.new', {
                parent: 'contentDefinition',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contentDefinition/contentDefinition-dialog.html',
                        controller: 'ContentDefinitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    version: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('contentDefinition', null, { reload: true });
                    }, function() {
                        $state.go('contentDefinition');
                    })
                }]
            })
            .state('contentDefinition.edit', {
                parent: 'contentDefinition',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contentDefinition/contentDefinition-dialog.html',
                        controller: 'ContentDefinitionDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ContentDefinition', function(ContentDefinition) {
                                return ContentDefinition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contentDefinition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('contentDefinition.delete', {
                parent: 'contentDefinition',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/contentDefinition/contentDefinition-delete-dialog.html',
                        controller: 'ContentDefinitionDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ContentDefinition', function(ContentDefinition) {
                                return ContentDefinition.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('contentDefinition', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
