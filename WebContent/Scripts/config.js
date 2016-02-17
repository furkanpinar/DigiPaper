'use strict';

App.config(function ($routeProvider, $locationProvider, $controllerProvider) {
    App.registerCtrl = $controllerProvider.register;

    $routeProvider
        .when('/:name', {
            templateUrl: function(urlattr){
                return 'ui/' + urlattr.name + '.html';
            }
        })
        .when('/:name/:id', {
            templateUrl: function(urlattr){
                return 'ui/' + urlattr.name + '.html';
            }
        });
    $routeProvider.otherwise({redirectTo: '/home'});
});

App.config(['$translateProvider', function ($translateProvider) {
    $translateProvider.translations('en', {
        'ABOUT': 'About',
        'HOME': 'Home',
        'PRODUCTS': 'Products',
        'CATEGORIES': 'Categories',
        'SERVICES':'Services',
        'CONTACT':'Contact',
        'LOGIN':'Login',
        'SIGNUP':'Sign Up',
        'SCREENSHOTS':'Screenshots',
        'REFERENCES':'References'
    });

    $translateProvider.translations('tr', {
        'ABOUT': 'Hakkımızda',
        'HOME': 'Anasayfa',
        'PRODUCTS': 'Ürünler',
        'CATEGORIES': 'Kategoriler',
        'SERVICES': 'Servisler',
        'CONTACT':'İletişim',
        'LOGIN':'Giriş',
        'SIGNUP':'Kayıt Ol',
        'SCREENSHOTS':'Ekranlar',
        'REFERENCES':'Referanslar'
    });

    $translateProvider.preferredLanguage('tr');
}]);