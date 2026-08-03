package robertovisconti.be_capstone_msartballet.services.utenti;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.*;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.UnauthorizedException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.*;
import robertovisconti.be_capstone_msartballet.repositories.utenti.*;
import robertovisconti.be_capstone_msartballet.tools.EmailSender;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UtenteRepository utenteRepository;
    private final AllievoRepository allievoRepository;
    private final InsegnanteRepository insegnanteRepository;
    private final OspiteRepository ospiteRepository;
    private final TokenAttivazioneRepository tokenAttivazioneRepository;
    private final TokenResetPasswordRepository tokenResetPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailSender emailSender;
    private final String frontendUrl;

    public AuthService(UtenteRepository utenteRepository, AllievoRepository allievoRepository, InsegnanteRepository insegnanteRepository, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, OspiteRepository ospiteRepository, TokenAttivazioneRepository tokenAttivazioneRepository, TokenResetPasswordRepository tokenResetPasswordRepository,
                       EmailSender emailSender, @Value("${app.frontendUrl}") String frontendUrl) {
        this.utenteRepository = utenteRepository;
        this.allievoRepository = allievoRepository;
        this.insegnanteRepository = insegnanteRepository;
        this.ospiteRepository = ospiteRepository;
        this.tokenAttivazioneRepository = tokenAttivazioneRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.emailSender = emailSender;
        this.frontendUrl = frontendUrl;
        this.tokenResetPasswordRepository = tokenResetPasswordRepository;
    }

    public Allievo registraAllievo(NewAllievoDTO body) {
        verificaEmailDisponibile(body.email());

        Allievo nuovoAllievo = new Allievo();
        nuovoAllievo.setNome(body.nome());
        nuovoAllievo.setCognome(body.cognome());
        nuovoAllievo.setEmail(body.email());
        nuovoAllievo.setDataDiNascita(body.dataDiNascita());
        nuovoAllievo.setImgProfilo(body.imgProfilo());
        nuovoAllievo.setRuolo(RuoloUtente.ALLIEVO);
        nuovoAllievo.setAccountAttivo(false);

        // campi opzionali in fase di registrazione
        nuovoAllievo.setNumeroScarpetta(body.numeroScarpetta());
        nuovoAllievo.setMarcaScarpetta(body.marcaScarpetta());
        nuovoAllievo.setHaPunte(body.haPunte() != null ? body.haPunte() : false);
        nuovoAllievo.setMarcaPunte(body.marcaPunte());
        nuovoAllievo.setLarghezzaPunte(body.larghezzaPunte());
        nuovoAllievo.setTagliaBody(body.tagliaBody());
        nuovoAllievo.setTagliaCalzini(body.tagliaCalzini());
        nuovoAllievo.setAltezzaCm(body.altezzaCm());
        nuovoAllievo.setTagliaPantalone(body.tagliaPantalone());
        nuovoAllievo.setDataScadenzaCertificato(body.dataScadenzaCertificato());
        nuovoAllievo.setContattoEmergenzaNome(body.contattoEmergenzaNome());
        nuovoAllievo.setContattoEmergenzaTelefono(body.contattoEmergenzaTelefono());
        nuovoAllievo.setCodiceFiscale(body.codiceFiscale());
        nuovoAllievo.setConsensoPrivacyFoto(body.consensoPrivacyFoto() != null ? body.consensoPrivacyFoto() : false);
        nuovoAllievo.setQuotaIscrizionePagata(false); // gestita dalla segreteria quando si iscrive a un corso

        Allievo allievoSalvato = allievoRepository.save(nuovoAllievo);
        generaTokenAttivazione(allievoSalvato);

        return allievoSalvato;
    }


    public Ospite registraOspite(OspiteRegistrazioneDTO body) {
        verificaEmailDisponibile(body.email());
        Ospite nuovoOspite = new Ospite(body.nome(), body.cognome(), body.email(), body.dataDiNascita());
        return ospiteRepository.save(nuovoOspite);

    }

    public Insegnante registraInsegnante(NewInsegnanteDTO body) {

        verificaEmailDisponibile(body.email());

        Insegnante nuovoInsegnante = new Insegnante();
        nuovoInsegnante.setNome(body.nome());
        nuovoInsegnante.setCognome(body.cognome());
        nuovoInsegnante.setEmail(body.email());
        nuovoInsegnante.setDataDiNascita(body.dataDiNascita());
        nuovoInsegnante.setImgProfilo(body.imgProfilo());
        nuovoInsegnante.setRuolo(RuoloUtente.INSEGNANTE);
        nuovoInsegnante.setBiografia(body.biografia());
        nuovoInsegnante.setAccountAttivo(false);

        Insegnante insegnanteSalvato = insegnanteRepository.save(nuovoInsegnante);
        generaTokenAttivazione(insegnanteSalvato);

        return insegnanteSalvato;
    }

    public Utente attivaAccount(AttivazioneAccountDTO body) {
        TokenAttivazione tokenAttivazione = tokenAttivazioneRepository.findByToken(body.token()).orElseThrow(() -> new BadRequestException("Token di attivazione non valido!"));
        if (Boolean.TRUE.equals(tokenAttivazione.getUtilizzato())) {
            throw new BadRequestException("Questo link di attivazione è già utilizzato!");
        }
        if (tokenAttivazione.getDataScadenza().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Il link di attivazione è scaduto, contatta la segreteria");
        }
        Utente utente = tokenAttivazione.getUtente();
        utente.setPassword(passwordEncoder.encode(body.nuovaPassword()));
        utente.setAccountAttivo(true);
        utenteRepository.save(utente);

        tokenAttivazione.setUtilizzato(true);
        tokenAttivazioneRepository.save(tokenAttivazione);

        return utente;
    }


    public Utente login(LoginDTO body) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.email(), body.password()));
            return (Utente) authentication.getPrincipal();
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Email o password non corretti");
        }
    }

    public void richiediResetPassword(RichiestaResetPasswordDTO body) {
        utenteRepository.findByEmail(body.email()).ifPresent(this::generaTokenResetPassword);
        // nessuna eccezione se l'email non esiste: evita di rivelare quali email sono registrate
    }

    public void reinviaAttivazione(RichiestaResetPasswordDTO body) {
        utenteRepository.findByEmail(body.email())
                .filter(utente -> !Boolean.TRUE.equals(utente.getAccountAttivo()))
                .ifPresent(this::generaTokenAttivazione);
        // stesso principio: nessuna eccezione se l'email non esiste o l'account è già attivo
    }

    public Utente resetPassword(ResetPasswordDTO body) {
        TokenResetPassword tokenResetPassword = tokenResetPasswordRepository.findByToken(body.token())
                .orElseThrow(() -> new BadRequestException("Token di reset non valido!"));
        if (Boolean.TRUE.equals(tokenResetPassword.getUtilizzato())) {
            throw new BadRequestException("Questo link di reset è già stato utilizzato!");
        }
        if (tokenResetPassword.getDataScadenza().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Il link di reset è scaduto, richiedine uno nuovo");
        }
        Utente utente = tokenResetPassword.getUtente();
        utente.setPassword(passwordEncoder.encode(body.nuovaPassword()));
        utenteRepository.save(utente);

        tokenResetPassword.setUtilizzato(true);
        tokenResetPasswordRepository.save(tokenResetPassword);

        return utente;
    }


    private void generaTokenAttivazione(Utente utente) {
        TokenAttivazione tokenAttivazione = new TokenAttivazione(UUID.randomUUID().toString(), LocalDateTime.now().plusDays(2), utente);
        TokenAttivazione tokenSalvato = tokenAttivazioneRepository.save(tokenAttivazione);

        String linkAttivazione = frontendUrl + "/attiva-account?token=" + tokenSalvato.getToken();

        String testo = "Ciao " + utente.getNome() + ",\n\n"
                + "Il tuo account è stato creato. Per attivarlo e impostare la tua password, apri questo link:\n"
                + linkAttivazione + "\n\n"
                + "Il link scade tra 2 giorni.";

        emailSender.inviaEmail(utente.getEmail(), "Attiva il tuo account", testo);
    }

    private void generaTokenResetPassword(Utente utente) {
        TokenResetPassword tokenResetPassword = new TokenResetPassword(UUID.randomUUID().toString(), LocalDateTime.now().plusHours(1), utente);
        TokenResetPassword tokenSalvato = tokenResetPasswordRepository.save(tokenResetPassword);

        String linkReset = frontendUrl + "/reset-password?token=" + tokenSalvato.getToken();

        String testo = "Ciao " + utente.getNome() + ",\n\n"
                + "Hai richiesto di reimpostare la password. Apri questo link per sceglierne una nuova:\n"
                + linkReset + "\n\n"
                + "Se non hai richiesto tu il reset, ignora questa email. Il link scade tra 1 ora.";

        emailSender.inviaEmail(utente.getEmail(), "Reimposta la tua password", testo);
    }


    private void verificaEmailDisponibile(String email) {
        if (utenteRepository.existsByEmail(email)) {
            throw new BadRequestException("L'email " + email + " è già in uso");
        }
    }
}

