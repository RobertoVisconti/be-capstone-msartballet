package robertovisconti.be_capstone_msartballet.controllers.utente;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Admin;
import robertovisconti.be_capstone_msartballet.entities.Allievo;
import robertovisconti.be_capstone_msartballet.entities.Insegnante;
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
        return UtenteMapper.mappaAllievo(nuovoAllievo);
    }

    @PostMapping("/admin/insegnanti")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public InsegnanteRespDTO registraInsegnante(@RequestBody @Valid NewInsegnanteDTO body) {
        Insegnante nuovoInsegnante = authService.registraInsegnante(body);
        return UtenteMapper.mappaInsegnante(nuovoInsegnante);
    }

    @PostMapping("/admin/admins")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminRespDTO registraAdmin(@RequestBody @Valid NewAdminDTO body) {
        Admin nuovoAdmin = authService.registraAdmin(body);
        return UtenteMapper.mappaAdmin(nuovoAdmin);
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

    @PostMapping("/password-dimenticata")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void passwordDimenticata(@RequestBody @Valid RichiestaResetPasswordDTO body) {
        authService.richiediResetPassword(body);
    }

    @PostMapping("/reinvia-attivazione")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reinviaAttivazione(@RequestBody @Valid RichiestaResetPasswordDTO body) {
        authService.reinviaAttivazione(body);
    }

    @PostMapping("/reset-password")
    public LoginRespDTO resetPassword(@RequestBody @Valid ResetPasswordDTO body) {
        Utente utente = authService.resetPassword(body);
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

}