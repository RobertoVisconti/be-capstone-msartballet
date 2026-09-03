package robertovisconti.be_capstone_msartballet.controllers.utente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.AdminRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.UtenteMapper;
import robertovisconti.be_capstone_msartballet.services.utenti.AdminService;

import java.util.UUID;

@RestController
@RequestMapping("/utenti/admins")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Page<AdminRespDTO> trovaTutti(@PageableDefault(size = 20) Pageable pageable) {
        return adminService.trovaTutti(pageable).map(UtenteMapper::mappaAdmin);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminRespDTO trovaPerId(@PathVariable UUID id) {
        return UtenteMapper.mappaAdmin(adminService.trovaPerId(id));
    }

    @PatchMapping("/{id}/disattiva")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminRespDTO disattiva(@PathVariable UUID id) {
        return UtenteMapper.mappaAdmin(adminService.disattiva(id));
    }

    @PatchMapping("/{id}/riattiva")
    @PreAuthorize("hasRole('ADMIN')")
    public AdminRespDTO riattiva(@PathVariable UUID id) {
        return UtenteMapper.mappaAdmin(adminService.riattiva(id));
    }
}
