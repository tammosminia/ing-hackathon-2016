var app = angular.module('Leeuw', []);
app.controller('LeeuwController', ['$http', '$interval', function($http, $interval){
    var leeuw = this;

    leeuw.balance = 111;

    leeuw.refresh = function() {
        $http.get("http://localhost:8089/knaken").
            success(function(data, status, headers, config) {
                leeuw.balance = data;
            }).
            error(function(data, status, headers, config) {
                leeuw.balance = "error " + status + " " + JSON.stringify(data);
            });
    };

    leeuw.brul = function() {
        $http.get("http://localhost:8089/brul").
            success(function(data, status, headers, config) {
            }).
            error(function(data, status, headers, config) {
            });
    };

    $interval(function(){
        leeuw.refresh();
    },1000);
}]);