'use strict';

angular.module('ecmspoc1App')
    .config(function ($stateProvider) {
        $stateProvider
            .state('deploymentGroup', {
                parent: 'entity',
                url: '/deploymentGroups',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeploymentGroups'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deploymentGroup/deploymentGroups.html',
                        controller: 'DeploymentGroupController'
                    }
                },
                resolve: {
                }
            })
            .state('deploymentGroup.detail', {
                parent: 'entity',
                url: '/deploymentGroup/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeploymentGroup'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/deploymentGroup/deploymentGroup-detail.html',
                        controller: 'DeploymentGroupDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DeploymentGroup', function($stateParams, DeploymentGroup) {
                        return DeploymentGroup.get({id : $stateParams.id});
                    }]
                }
            })
            .state('deploymentGroup.new', {
                parent: 'deploymentGroup',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deploymentGroup/deploymentGroup-dialog.html',
                        controller: 'DeploymentGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    packagingType: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('deploymentGroup', null, { reload: true });
                    }, function() {
                        $state.go('deploymentGroup');
                    })
                }]
            })
            .state('deploymentGroup.edit', {
                parent: 'deploymentGroup',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deploymentGroup/deploymentGroup-dialog.html',
                        controller: 'DeploymentGroupDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeploymentGroup', function(DeploymentGroup) {
                                return DeploymentGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deploymentGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('deploymentGroup.delete', {
                parent: 'deploymentGroup',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/deploymentGroup/deploymentGroup-delete-dialog.html',
                        controller: 'DeploymentGroupDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeploymentGroup', function(DeploymentGroup) {
                                return DeploymentGroup.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('deploymentGroup', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
