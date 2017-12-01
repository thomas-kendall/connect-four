// Create the application
var app = angular.module('app', ['ngRoute']);

// Setup routing
app.config(function ($routeProvider) {
    $routeProvider
        .when("/", {
            templateUrl: "app/views/home.html"
        })
        .when("/play", {
            templateUrl: "app/views/play.html"
        })
        .otherwise({
            redirectTo: '/'
        });
});

// Angular 1.6 added a hash-prefix. We don't want that.
app.config(['$locationProvider', function ($locationProvider) {
    $locationProvider.hashPrefix('');
}]);