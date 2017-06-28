package br.com.cellenti.site.excecoes;

public class CellentiException extends Exception {

        public CellentiException() {
                super();
        }
        
        public CellentiException(String message) {
                super(message);
        }

        public CellentiException(Throwable cause) {
                super(cause);
        }
        
}
