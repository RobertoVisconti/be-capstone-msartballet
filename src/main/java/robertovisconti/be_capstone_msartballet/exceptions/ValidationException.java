package robertovisconti.be_capstone_msartballet.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<String> listaErrori;

    public ValidationException(String message, List<String> listaErrori) {
        super("Ci sono stati errori di validazione");
        this.listaErrori = listaErrori;
    }

    public List<String> getListaErrori() {
        return listaErrori;
    }
}
