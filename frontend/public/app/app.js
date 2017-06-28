var appMain = angular.module("app", ["ngRoute", "ngCookies", "ngResource", "restangular", "ui.bootstrap", "app.constants"]);

appMain.filter('SimNao', function(){
    return function(text){
      return text ? "Sim" : "Não";
    }
});
