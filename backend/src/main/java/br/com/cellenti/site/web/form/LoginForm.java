package br.com.cellenti.site.web.form;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class LoginForm {

        private String grant_type;
        private String client_id;
        private String client_secret;
        private String username;
        private String password;

        public LoginForm() {
        }
}
