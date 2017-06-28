package br.com.cellenti.site.servicos.repositorio;

import br.com.cellenti.site.modelos.Contato;
import org.springframework.data.repository.CrudRepository;

public interface ContatoRepository extends CrudRepository<Contato, Long> {
        
}
