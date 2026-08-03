package robertovisconti.be_capstone_msartballet.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.entities.Ospite;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.*;
import robertovisconti.be_capstone_msartballet.security.TokenToolkit;
import robertovisconti.be_capstone_msartballet.services.utenti.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final TokenToolkit tokenToolkit;

    public AuthController(AuthService authService, TokenToolkit tokenToolkit) {
        this.authService = authService;
        this.tokenToolkit = tokenToolkit;
    }

    @PostMapping("/admin/allievi")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AllievoRespDTO registraAllievo(@RequestBody @Valid NewAllievoDTO body) {
        Allievo nuovoAllievo = authService.registraAllievo(body);
        return mappaAllievo(nuovoAllievo);
    }

    @PostMapping("/admin/insegnanti")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InsegnanteRespDTO registraInsegnante(@RequestBody @Valid NewInsegnanteDTO body) {
        Insegnante nuovoInsegnante = authService.registraInsegnante(body);
        return mappaInsegnante(nuovoInsegnante);
    }

    @PostMapping("/register/ospite")
    @ResponseStatus(HttpStatus.CREATED)
    public OspiteRespDTO registraOspite(@RequestBody @Valid OspiteRegistrazioneDTO body) {
        Ospite nuovoOspite = authService.registraOspite(body);
        return mappaOspite(nuovoOspite);
    }

    @PostMapping("/attiva-account")
    public LoginRespDTO attivaAccount(@RequestBody @Valid AttivazioneAccountDTO body) {
        Utente utente = authService.attivaAccount(body);
        String token = tokenToolkit.tokenGenerator(utente);

        return new LoginRespDTO(
                token,
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getRuolo()
        );
    }

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody @Valid LoginDTO body) {
        Utente utente = authService.login(body);
        String token = tokenToolkit.tokenGenerator(utente);

        return new LoginRespDTO(
                token,
                utente.getId(),
                utente.getNome(),
                utente.getCognome(),
                utente.getRuolo()
        );
    }


    private AllievoRespDTO mappaAllievo(Allievo allievo) {
        return new AllievoRespDTO(
                allievo.getId(),
                allievo.getNome(),
                allievo.getCognome(),
                allievo.getEmail(),
                allievo.getDataDiNascita(),
                allievo.getImgProfilo(),
                allievo.getRuolo(),
                allievo.getDataRegistrazione(),
                allievo.getAccountAttivo(),
                allievo.getNumeroScarpetta(),
                allievo.getMarcaScarpetta(),
                allievo.getHaPunte(),
                allievo.getMarcaPunte(),
                allievo.getLarghezzaPunte(),
                allievo.getTagliaBody(),
                allievo.getTagliaCalzini(),
                allievo.getAltezzaCm(),
                allievo.getTagliaPantalone(),
                allievo.getDataScadenzaCertificato(),
                allievo.getContattoEmergenzaNome(),
                allievo.getContattoEmergenzaTelefono(),
                allievo.getCodiceFiscale(),
                allievo.getQuotaIscrizionePagata(),
                allievo.getConsensoPrivacyFoto(),
                allievo.getNoteSegreteria()
        );
    }

    private InsegnanteRespDTO mappaInsegnante(Insegnante insegnante) {
        return new InsegnanteRespDTO(
                insegnante.getId(),
                insegnante.getNome(),
                insegnante.getCognome(),
                insegnante.getEmail(),
                insegnante.getDataDiNascita(),
                insegnante.getImgProfilo(),
                insegnante.getRuolo(),
                insegnante.getDataRegistrazione(),
                insegnante.getAccountAttivo(),
                insegnante.getBiografia()
        );
    }

    private OspiteRespDTO mappaOspite(Ospite ospite) {
        return new OspiteRespDTO(
                ospite.getId(),
                ospite.getNome(),
                ospite.getCognome(),
                ospite.getEmail(),
                ospite.getDataDiNascita(),
                ospite.getImgProfilo(),
                ospite.getRuolo(),
                ospite.getDataRegistrazione()
        );
    }


}
