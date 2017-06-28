appMain.controller("HomeController", function (HomeService) {
        var ctrl = this;

        ctrl.init = function () {
                ctrl.form = {};

                ctrl.form.variavel = "exemplo de conteudo em variavel";
                ctrl.form.valorServico = HomeService.retornarValor();
        };
        
        ctrl.init();
});

