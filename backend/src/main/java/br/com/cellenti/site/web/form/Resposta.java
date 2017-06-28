package br.com.cellenti.site.web.form;

import br.com.cellenti.site.util.FormatadorUtil;
import br.com.cellenti.site.web.util.OptionsFilter;
import java.io.Serializable;
import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.transaction.TransactionSystemException;

public class Resposta implements Serializable {

        private static final int HTTP_STATUS_ERROR = 500;
        private static final int HTTP_STATUS_VALIDATION = 406;
        private static final int HTTP_STATUS_SUCCESS = 200;

        private Object dado;
        private List lista;
        private List<Retorno> mensagens;

        public void setDado(Object modelo) {
                this.setDado(modelo, null, null);
        }
        
        public void setDado(Object modelo, HttpServletResponse resp) {
                this.setDado(modelo, resp, "sucesso");
        }
        
        public void setDado(Object modelo, HttpServletResponse resp, String msg) {
                this.setDado(modelo, resp, HTTP_STATUS_SUCCESS, "sucesso");
        }
        
        public void setDado(Object modelo, HttpServletResponse resp, int status, String msg) {
                this.dado = modelo;
                if(resp!=null){
                        this.configureResponse(status, resp);
                }
                if(msg!=null){
                        getMensagens().add(new Retorno("mensagem", FormatadorUtil.getMessage(msg)));
                }
        }

        public void setLista(List lista, HttpServletResponse resp) {
                this.setLista(lista, resp, "sucesso");
        }

        public void setLista(List lista, HttpServletResponse resp, String msg) {
                this.lista = lista;
                this.configureResponse(HTTP_STATUS_SUCCESS, resp);
                getMensagens().add(new Retorno("mensagem", FormatadorUtil.getMessage(msg)));
        }

        public void setMensagemSucesso(String msg, HttpServletResponse resp) {
                this.configureResponse(HTTP_STATUS_SUCCESS, resp);
                getMensagens().add(new Retorno("mensagem", FormatadorUtil.getMessage(msg)));
        }

        /**
         * Adiciona qualquer mensagem de erro, alterando o Status HTTP relativo.
         * Pode tratar erros SQL nativo, como unique.
         *
         * @param ex Exception
         * @param resp HttpServletResponse
         */
        public void addErroGenerico(Exception ex, HttpServletResponse resp) {
                Throwable cause = ex.getCause();
                this.configureResponse(HTTP_STATUS_ERROR, resp);
                if (cause instanceof org.hibernate.exception.ConstraintViolationException) {
                        org.hibernate.exception.ConstraintViolationException hib = (org.hibernate.exception.ConstraintViolationException) cause;
                        if (hib.getCause() instanceof BatchUpdateException) {
                                BatchUpdateException bete = ((BatchUpdateException) hib.getCause());
                                String str = bete.getNextException().getMessage();
                                if (str != null && str.toLowerCase().contains("duplicate key")) {
                                        getMensagens().add(new Retorno("erro", "Campo duplicado."));
                                        return;
                                }
                        }
                }
                getMensagens().add(new Retorno("mensagem", ex.getMessage()));
        }

        /**
         * Adiciona qualquer mensagem de validacao gerada a partir do nao
         * cumprimento de regras de negocio.
         *
         * @param str String
         * @param resp HttpServletResponse
         */
        public void addErro(String str, HttpServletResponse resp) {
                this.configureResponse(HTTP_STATUS_ERROR, resp);
                getMensagens().add(new Retorno("mensagem", FormatadorUtil.getMessage(str)));
        }
        
        public void addValidacao(String str, HttpServletResponse resp) {
                this.configureResponse(HTTP_STATUS_VALIDATION, resp);
                getMensagens().add(new Retorno("mensagem", FormatadorUtil.getMessage(str)));
        }
        
        /**
         * Adiciona qualquer mensagem de validacao de campos BeanValidator,
         * alterando o Status HTTP relativo.
         *
         * @param ex TransactionSystemException
         * @param resp HttpServletResponse
         */
        public void addErros(TransactionSystemException ex, HttpServletResponse resp) {
                if (ex.getRootCause() instanceof ConstraintViolationException) {
                        ConstraintViolationException exContrain = (ConstraintViolationException) ex.getRootCause();
                        Set<ConstraintViolation<?>> constraintViolations = exContrain.getConstraintViolations();
                        if (constraintViolations != null) {
                                for (ConstraintViolation cons : constraintViolations) {
                                        getMensagens().add(new Retorno(cons.getPropertyPath().toString(), cons.getMessage()));
                                }
                                this.configureResponse(HTTP_STATUS_VALIDATION, resp);
                        }
                } else {
                        this.addErroGenerico(ex, resp);
                }
        }

        public List<Retorno> getMensagens() {
                if (mensagens == null) {
                        mensagens = new ArrayList<>();
                }
                return mensagens;
        }

        public List getLista() {
                return lista;
        }

        public Object getDado() {
                return dado;
        }

        /**
         * Indica campos ou chaves com seus respectivos valores sobre erros e
         * validacoes.
         */
        public static class Retorno implements Serializable {

                private String chave;
                private String valor;

                public Retorno(String chave, String valor) {
                        this.chave = chave;
                        this.valor = valor;
                }

                public String getChave() {
                        return chave;
                }

                public String getValor() {
                        return valor;
                }

        }
        
        private void configureResponse(int status, HttpServletResponse response) {
		if (response != null) {
                        new OptionsFilter().configCorsResponse(response);
			response.setContentType("application/json");
			response.setStatus(status);
		}
	}
}
