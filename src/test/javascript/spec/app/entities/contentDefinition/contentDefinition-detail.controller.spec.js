'use strict';

describe('Controller Tests', function() {

    describe('ContentDefinition Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockContentDefinition, MockDeploymentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockContentDefinition = jasmine.createSpy('MockContentDefinition');
            MockDeploymentGroup = jasmine.createSpy('MockDeploymentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ContentDefinition': MockContentDefinition,
                'DeploymentGroup': MockDeploymentGroup
            };
            createController = function() {
                $injector.get('$controller')("ContentDefinitionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecmspoc1App:contentDefinitionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
