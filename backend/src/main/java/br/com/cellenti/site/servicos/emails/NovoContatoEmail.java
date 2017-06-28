package br.com.cellenti.site.servicos.emails;

import br.com.cellenti.site.modelos.Contato;
import br.com.cellenti.site.web.util.EmailBase;

public class NovoContatoEmail extends EmailBase {
        
        public NovoContatoEmail(Contato contato) {
		super.addTo("contato@cellenti.com.br");
                
		super.setSubject("Cellenti - Novo Contato");
		super.setTitle("Cellenti - Novo Contato");
		super.setPage("novo-contato.html");
                
		super.addData("contato-nome", contato.getNome());
                super.addData("contato-email", contato.getEmail());
                super.addData("contato-texto", contato.getTexto());
        }

}
