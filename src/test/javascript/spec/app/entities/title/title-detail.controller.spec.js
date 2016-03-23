'use strict';

describe('Controller Tests', function() {

    describe('Title Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockTitle, MockCatalog, MockContentDefinition;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockTitle = jasmine.createSpy('MockTitle');
            MockCatalog = jasmine.createSpy('MockCatalog');
            MockContentDefinition = jasmine.createSpy('MockContentDefinition');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Title': MockTitle,
                'Catalog': MockCatalog,
                'ContentDefinition': MockContentDefinition
            };
            createController = function() {
                $injector.get('$controller')("TitleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'ecmspoc1App:titleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
