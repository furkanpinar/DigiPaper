App.factory('MyService', function($http, $location, $rootScope, $route) {

    //CONSTRUCTOR
    function MyService(scope, path) {
        this.scope = scope;
        this.scope.model = {};
        this.scope.models = {};
        this.scope.action = null;
        this.scope.messages = {};
        this.url = $rootScope.path + path;

        if ($route.current.params.id != null) {
            this.View($route.current.params.id);
        } else {
            this.New();
        }
    }

    MyService.prototype.Init = function () {
        this.getAll();
    };

    MyService.prototype.getAll = function () {
        var myObject = this;
        $http.get(this.url + '/getAll', null, null)
            .success(function (result) {
                myObject.scope.models = result.datas;
                myObject.scope.action = result.action;
                myObject.scope.messages = result.messages;
            })
            .error(function (res, status) {
                unhandledException(res, status);
            });
    };

    MyService.prototype.getAutoCompleteValues = function () {
        var myObject = this;
        $http.get(this.url + '/getAutoCompleteValues', null, null)
            .success(function (result) {
                myObject.scope.categories = result.datas;
            })
            .error(function (res, status) {
                unhandledException(res, status);
            });
    };

    MyService.prototype.Refresh = function() {
        this.getAll();
        this.New();
    };

    //NEW
    MyService.prototype.New = function() {
        this.scope.model = null;
    };

    //SAVE
    MyService.prototype.Save = function() {
        var myObject = this;
        this.scope.request = {
            action : "save",
            data : myObject.scope.model
        };
        $http.post(this.url + '/save', this.scope.request, null).success(function(result) {
            myObject.scope.model = result.data;
            myObject.getAll();
        }).error(function (res, status) {
            unhandledException(res, status);
        });
    };

    //DELETE
    MyService.prototype.Delete = function() {
        var myObject = this;
        this.scope.request = {
            action : "delete",
            data : myObject.scope.model
        };
        $http.post(this.url + '/delete', this.scope.request, null).success(function(result) {
            myObject.scope.model = result.data;
            myObject.getAll();
        }).error(function (res, status) {
            unhandledException(res, status);
        });
    };

    //VIEW
    MyService.prototype.View = function(id) {
        var myObject = this;

        $http.get(this.url + '/get/' + id, null, null)
            .success(function (result) {
                myObject.scope.model = result.data;
                myObject.scope.action = result.action;
                myObject.scope.messages = result.messages;
            })
            .error(function (res, status) {
                unhandledException(res, status);
            });
    };

    //OTHER
    MyService.prototype.Other = function(action, request) {
        var myObject = this;
        this.scope.request = request;
        this.scope.request.action = act;
        this.scope.request.url = this.url;
        return $http.post(this.url, this.scope.request, null).success(function(response) {
            myObject.scope.response = response;
        }).error(function (response, status) {
            unhandledException(response, status);
        });
    };

    return MyService;
});