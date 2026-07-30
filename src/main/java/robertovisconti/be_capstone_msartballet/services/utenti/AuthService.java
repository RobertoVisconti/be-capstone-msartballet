package robertovisconti.be_capstone_msartballet.services.utenti;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.enums.RuoloUtente;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.UnauthorizedException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.NewAllievoDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.NewInsegnanteDTO;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AllievoRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.InsegnanteRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;

@Service
public class AuthService {

    private UtenteRepository utenteRepository;
    private AllievoRepository allievoRepository;
    private InsegnanteRepository insegnanteRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;

    public AuthService(UtenteRepository utenteRepository, AllievoRepository allievoRepository, InsegnanteRepository insegnanteRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.utenteRepository = utenteRepository;
        this.allievoRepository = allievoRepository;
        this.insegnanteRepository = insegnanteRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public Allievo registraAllievo(NewAllievoDTO body) {
        verificaEmailDisponibile(body.email());

        Allievo nuovoAllievo = new Allievo();
        nuovoAllievo.setNome(body.nome());
        nuovoAllievo.setCognome(body.cognome());
        nuovoAllievo.setEmail(body.email());
        nuovoAllievo.setPassword(body.password());
        nuovoAllievo.setDataDiNascita(body.dataDiNascita());
        nuovoAllievo.setImgProfilo(body.imgProfilo());
        nuovoAllievo.setRuolo(RuoloUtente.ALLIEVO);

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

        return allievoRepository.save(nuovoAllievo);
    }

    public Insegnante registraInsegnante(NewInsegnanteDTO body) {

        verificaEmailDisponibile(body.email());

        Insegnante nuovoInsegnante = new Insegnante();
        nuovoInsegnante.setNome(body.nome());
        nuovoInsegnante.setCognome(body.cognome());
        nuovoInsegnante.setEmail(body.email());
        nuovoInsegnante.setPassword(body.password());
        nuovoInsegnante.setDataDiNascita(body.dataDiNascita());
        nuovoInsegnante.setImgProfilo(body.imgProfilo());
        nuovoInsegnante.setRuolo(RuoloUtente.INSEGNANTE);
        nuovoInsegnante.setBiografia(body.biografia());

        return insegnanteRepository.save(nuovoInsegnante);
    }

    public Utente login(LoginDTO body) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(body.email(), body.password()));
            return (Utente) authentication.getPrincipal();
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Email o password non corretti");
        }
    }


    private void verificaEmailDisponibile(String email) {
        if (utenteRepository.existsByEmail(email)) {
            throw new BadRequestException("L'email " + email + " è già in uso");
        }
    }

}
