package br.com.cellenti.site.modelos;

import br.com.cellenti.site.util.Modelos;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Contato extends Modelos {
        
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "contatoGenerator")
	@TableGenerator(name = "contatoGenerator", allocationSize = 1)
	private Long id;
        
        @Size(max = 2000)
        @NotNull
        @NotEmpty
        private String texto;
        
        @NotNull
        @NotEmpty
        private String nome;
        
        @NotNull
        @NotEmpty
        private String email;
        
        @Override
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTexto() {
                return texto;
        }

        public void setTexto(String texto) {
                this.texto = texto;
        }

        public String getNome() {
                return nome;
        }

        public void setNome(String nome) {
                this.nome = nome;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

}
