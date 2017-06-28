package br.com.cellenti.site.web;

import br.com.cellenti.site.web.form.Resposta;
import br.com.cellenti.site.web.util.CellentiController;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/")
public class AppController extends CellentiController {
        
        @ResponseBody
        @RequestMapping(value = "/teste", method = RequestMethod.GET)
        public Resposta teste(HttpServletResponse resp) {
                Resposta resposta = new Resposta();
                resposta.setMensagemSucesso("mensagem.teste", resp);
                return resposta;
        }

}
