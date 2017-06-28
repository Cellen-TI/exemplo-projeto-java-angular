package br.com.cellenti.site.servicos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ParametrosSistema {
        private static final String ENV_DEVELOP = "DESENVOLVIMENTO";

	@Value("#{properties['ambiente']}")
	private String ambiente;
        
        @Value("#{properties['url.www']}")
	private String urlFront;

        @Value("#{properties['url.api']}")
	private String urlApi;

        @Value("#{properties['email.from.address']}")
	private String emailFromAddress;

	@Value("#{properties['email.from.name']}")
	private String emailFromName;

        public boolean isDesenvolvimento() {
		return ENV_DEVELOP.equalsIgnoreCase(ambiente);
	}

        public String getUrlFront() {
		return urlFront;
	}
        
        public String getUrlApi() {
		return urlApi;
	}

        public String getEmailFromAddress() {
		return emailFromAddress;
	}

	public String getEmailFromName() {
		return emailFromName;
	}

}
