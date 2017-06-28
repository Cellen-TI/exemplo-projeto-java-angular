appMain.config(function (RestangularProvider, $routeProvider, $httpProvider) {

        //PRA INDICAR AO SERVER QUE EH UMA REQUEST DO ANGULAR/AJAX
        RestangularProvider.setDefaultHeaders({'Content-Type': 'application/json'}); //para o services
        $httpProvider.defaults.headers.common['Content-Type'] = 'application/json';        
        $httpProvider.defaults.headers.common['If-Modified-Since'] = 'Mon, 26 Jul 1997 05:00:00 GMT';
        $httpProvider.defaults.headers.common['Cache-Control'] = 'no-cache';
        $httpProvider.defaults.headers.common['Pragma'] = 'no-cache';
        $httpProvider.defaults.cache = false;

        $routeProvider.when("/home/", {
                templateUrl:"/views/home.html",
                controller: "HomeController",
                controllerAs: "ctrl"
        });
});