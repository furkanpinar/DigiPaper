App.directive( 'goClick', function ( $location ) {
    return function ( scope, element, attrs ) {
        var path;

        attrs.$observe( 'goClick', function (val) {
            path = val;
        });

        element.bind( 'click', function () {
            scope.$apply( function () {
                $location.path( path );
            });
        });
    };
});

App.directive('messages', function() {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: false,
        template: '<div ng-repeat="message in res.messages" class="alert alert-{{message.type}} alert-dismissable" role="alert"><button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button><strong>{{message.title}}</strong> {{message.text}}</div>'
    }
});

App.directive('ajaxloader', ['$http', function ($http) {
    return {
        restrict: 'E',
        template:'<img src="/GlobalAssign/common/img/loader.gif" width="100px" height="100px" style="position: fixed; right: 0; bottom: 0;"/>',
        link: function (scope, elm, attrs) {
            scope.isLoading = function () {
                return $http.pendingRequests.length > 0;
            };

            scope.$watch(scope.isLoading, function (v) {
                if (v) {
                    elm.show();
                } else {
                    elm.hide();
                }
            });
        }
    };
}]);

App.directive('new', function() {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: false,
        template: '<button type="submit" class="btn btn-primary" ng-click="generic.actNew()"><span class="glyphicon glyphicon-file" /> New</button>'
    }
});

App.directive('save', function() {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: false,
        template: '<button type="submit" class="btn btn-primary" ng-click="generic.actSave()"><span class="glyphicon glyphicon-floppy-disk" /> Save</button>'
    }
});

App.directive('delete', function() {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: false,
        template: '<button type="submit" class="btn btn-primary" ng-click="generic.actDelete()"><span class="glyphicon glyphicon-trash" /> Delete</button>'
    }
});

App.directive('search', function() {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: false,
        template: '<button type="submit" class="btn btn-primary" ng-click="generic.actSearch()"><span class="glyphicon glyphicon-search" /> Search</button>'
    }
});

App.directive('myCustomer', function() {
    return {
        templateUrl: function(elem, attr){
            return 'customer-'+attr.type+'.html';
        }
    };
});