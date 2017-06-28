appMain.service("HomeService", ["Restangular", "CONST", function (Restangular, CONST) {

                this.retornarValor = function () {
                        return "valor no servico";
                }

                //exemplo de chamada ao um servidor rest POST. O retorno sera um promisse 'then'.
                /**
                this.enviar = function (form) {
                        return Restangular.one(CONST.url + "url/servidor").customPOST(form);
                }
                */
        }
]);
