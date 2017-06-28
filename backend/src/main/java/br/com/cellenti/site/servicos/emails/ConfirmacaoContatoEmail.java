package br.com.cellenti.site.servicos.emails;

import br.com.cellenti.site.modelos.Contato;
import br.com.cellenti.site.web.util.EmailBase;

public class ConfirmacaoContatoEmail extends EmailBase {
        
        public ConfirmacaoContatoEmail(Contato contato) {
		super.addTo(contato.getEmail());
		super.setSubject("Cellenti - Contato");
		super.setTitle("Cellenti - Contato");
		super.setPage("confirmacao-contato.html");
                
		super.addData("contato-nome", contato.getNome());
        }

}
