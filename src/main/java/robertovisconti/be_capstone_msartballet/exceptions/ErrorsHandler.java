package robertovisconti.be_capstone_msartballet.exceptions;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.errorsDTO.ErrorsDTO;


import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> listaErrori = ex.getBindingResult().getFieldErrors().stream()
                .map(errore -> errore.getField() + ": " + errore.getDefaultMessage())
                .collect(Collectors.toList());

        ex.getBindingResult().getGlobalErrors().forEach(errore ->
                listaErrori.add(errore.getObjectName() + ": " + errore.getDefaultMessage())
        );

        return new ErrorsDTO("Ci sono stati errori di validazione", LocalDateTime.now(), listaErrori);
    }

    // 1. GESTIONE ERRORI DI VALIDAZIONE (400 Bad Request)
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleValidationExceptions(ValidationException ex) {
        log.warn("Errore di validazione: {}", ex.getMessage());
        return new ErrorsDTO("Ci sono stati errori di validazione", LocalDateTime.now(), ex.getListaErrori());
    }

    // 2. GESTIONE BAD REQUEST GENERICHE (400 Bad Request)
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleBadRequest(BadRequestException ex) {
        log.warn("Bad request: {}", ex.getMessage());
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    // 3. GESTIONE NON AUTORIZZATO (401 Unauthorized)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleUnauthorized(UnauthorizedException ex) {
        log.warn("Non autorizzato: {}", ex.getMessage());
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    // 4. GESTIONE NON TROVATO (404 Not Found)
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsDTO handleNotFound(NotFoundException ex) {
        log.warn("Risorsa non trovata: {}", ex.getMessage());
        return new ErrorsDTO(ex.getMessage(), LocalDateTime.now());
    }

    // 5. GESTIONE ACCESSO NEGATO (403 Acced Denied )
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorsDTO handleAccessDenied(AccessDeniedException ex) {
        log.warn("Accesso negato: {}", ex.getMessage());
        return new ErrorsDTO("Non hai i permessi per eseguire questa operazione", LocalDateTime.now());
    }

    // 6. GESTIONE PARAMETRO O PATH VARIABLE CON TIPO ERRATO (400 Bad Request)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String messaggio = "Il valore '" + ex.getValue() + "' non è valido per il parametro '" + ex.getName() + "'";
        return new ErrorsDTO(messaggio, LocalDateTime.now());
    }

    // 7. GESTIONE CORPO DELLA RICHIESTA NON LEGGIBILE / JSON MALFORMATO (400 Bad Request)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsDTO handleMalformedJson(HttpMessageNotReadableException ex) {
        return new ErrorsDTO("Il corpo della richiesta non è un JSON valido", LocalDateTime.now());
    }

    // 8. GESTIONE VINCOLO DI INTEGRITÀ REFERENZIALE (409 Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsDTO handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.warn("Vincolo di integrità violato: {}", ex.getMostSpecificCause().getMessage());
        return new ErrorsDTO(
                "Impossibile completare l'operazione: la risorsa è collegata ad altri dati esistenti.",
                LocalDateTime.now()
        );
    }

    // 9. GESTIONE DI QUALSIASI ALTRA ECCEZIONE IMPREVISTA (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsDTO handleGenericError(Exception ex) {
        log.warn("Errore interno del server", ex);
        return new ErrorsDTO("Errore interno del server: " + ex.getMessage(), LocalDateTime.now());
    }
}


