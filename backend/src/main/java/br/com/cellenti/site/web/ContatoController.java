package br.com.cellenti.site.web;

import br.com.cellenti.site.modelos.Contato;
import br.com.cellenti.site.servicos.EmailServico;
import br.com.cellenti.site.servicos.emails.ConfirmacaoContatoEmail;
import br.com.cellenti.site.servicos.emails.NovoContatoEmail;
import br.com.cellenti.site.servicos.repositorio.ContatoRepository;
import br.com.cellenti.site.web.form.Resposta;
import br.com.cellenti.site.web.util.CellentiController;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/contato/")
public class ContatoController extends CellentiController {
        
        @Autowired
        private ContatoRepository contatoRepository;
        
        @Autowired
        private EmailServico emailServico;
        
        @ResponseBody
        @RequestMapping(value = "/cadastrar", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public Resposta cadastrar(@RequestBody Contato contato, HttpServletResponse resp) {
                contatoRepository.save(contato);
                emailServico.send(new ConfirmacaoContatoEmail(contato));
                emailServico.send(new NovoContatoEmail(contato));
                
                Resposta resposta = new Resposta();
                resposta.setMensagemSucesso("contato.sucesso", resp);
                return resposta;
        }

}
