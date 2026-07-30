package robertovisconti.be_capstone_msartballet.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.loginDTO.LoginRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AllievoRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.InsegnanteRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.NewAllievoDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.NewInsegnanteDTO;
import robertovisconti.be_capstone_msartballet.security.TokenToolkit;
import robertovisconti.be_capstone_msartballet.services.utenti.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;
    private TokenToolkit tokenToolkit;

    public AuthController(AuthService authService, TokenToolkit tokenToolkit) {
        this.authService = authService;
        this.tokenToolkit = tokenToolkit;
    }

    @PostMapping("/register/allievo")
    @ResponseStatus(HttpStatus.CREATED)
    public AllievoRespDTO registraAllievo(@RequestBody @Valid NewAllievoDTO body) {
        Allievo nuovoAllievo = authService.registraAllievo(body);
        return mappaAllievo(nuovoAllievo);
    }

    @PostMapping("/register/insegnante")
    @ResponseStatus(HttpStatus.CREATED)
    public InsegnanteRespDTO registraInsegnante(@RequestBody @Valid NewInsegnanteDTO body) {
        Insegnante nuovoInsegnante = authService.registraInsegnante(body);
        return mappaInsegnante(nuovoInsegnante);
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
                insegnante.getBiografia()
        );
    }
}
