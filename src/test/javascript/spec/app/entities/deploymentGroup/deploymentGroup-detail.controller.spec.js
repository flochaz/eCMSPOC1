'use strict';

describe('Controller Tests', function() {

    describe('DeploymentGroup Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockDeploymentGroup;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockDeploymentGroup = jasmine.createSpy('MockDeploymentGroup');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'DeploymentGroup': MockDeploymentGroup
            };
            createController = function() {
                $injector.get('$controller')("DeploymentGroupDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecmspoc1App:deploymentGroupUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
