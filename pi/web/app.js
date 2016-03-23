var app = angular.module('pushPoc', []);
app.controller('PushController', ['$http', function($http){
    var push = this;

    push.token = "";
    push.message = "Hello";
    push.deviceType = "Android";
    push.sending = "";

    push.sendMessage = function() {
        push.sending = "sending";

        $http.post("messages",
            {
                token: push.token,
                deviceType: push.deviceType,
                message: push.message,
                app: "testPush",
                requester: "intresting"
            }).
            success(function(data, status, headers, config) {
                push.sending = "sent. " + status + " " + JSON.stringify(data);
            }).
            error(function(data, status, headers, config) {
                push.sending = "error " + status + " " + JSON.stringify(data);
            });
    };

}]);