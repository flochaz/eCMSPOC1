'use strict';

describe('Controller Tests', function() {

    describe('Catalog Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockCatalog, MockAirline, MockDeploymentGroup, MockTitle;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockCatalog = jasmine.createSpy('MockCatalog');
            MockAirline = jasmine.createSpy('MockAirline');
            MockDeploymentGroup = jasmine.createSpy('MockDeploymentGroup');
            MockTitle = jasmine.createSpy('MockTitle');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Catalog': MockCatalog,
                'Airline': MockAirline,
                'DeploymentGroup': MockDeploymentGroup,
                'Title': MockTitle
            };
            createController = function() {
                $injector.get('$controller')("CatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecmspoc1App:catalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
