App.run(function($rootScope, $translate, $http, $location) {
    $rootScope.path = "http://localhost:8080/DigiPaper/rest/";
    $rootScope.root = "http://localhost:8080/DigiPaper";

    /*
    //SESSION person aliniyor
    $http.get($rootScope.path + 'session/person', null, null).success(function(res) {
        $rootScope.loggedPerson = res;
    }).error(function (res, status) {
        unhandledException(res, status);
    });
    */
    //LANGUAGE Dil degistirme
    $rootScope.changeLanguage = function (langKey) {
        $translate.use(langKey);
    };

    //EXCEPTION Tanimsiz hatalarda sayfaya yonlendiriyoruz 404.html vb.
    $rootScope.unhandledException = function(res, status) {
        $location.url(status);
    };

    //LOGOUT
    $rootScope.logout = function() {
        var req = {};
        req.act = 'logout';
        req.url = $rootScope.path + 'login';
        return $http.post($rootScope.path + 'login', req, null).success(function(res) {
            $rootScope.loggedPerson = null;
            window.location.href = $rootScope.root + "/site/#/login";
        }).error(function (res, status) {
            unhandledException(res, status);
        });
    };

    //LOGIN
    $rootScope.login = function(email, password) {
        var req = {};
        req.act = 'login';
        req.url = $rootScope.path + 'login';
        req.email = email;
        req.password = password;
        return $http.post($rootScope.path + 'login', req, null).success(function(res) {
            if (res.person != null) {
                $rootScope.loggedPerson = res.person;
                $location.url('home');
            }
        }).error(function (res, status) {
            unhandledException(res, status);
        });
    };
});