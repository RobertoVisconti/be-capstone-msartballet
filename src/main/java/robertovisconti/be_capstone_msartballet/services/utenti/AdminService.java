package robertovisconti.be_capstone_msartballet.services.utenti;

import org.springframework.stereotype.Service;
import robertovisconti.be_capstone_msartballet.entities.Admin;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AdminRepository;

import java.util.UUID;

@Service
public class AdminService {

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin trovaPerId(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun admin trovato con id " + id));
    }

    public Admin disattiva(UUID id) {
        Admin admin = trovaPerId(id);
        if (Boolean.TRUE.equals(admin.getProtetto())) {
            throw new BadRequestException("Questo admin è protetto e non può essere disattivato");
        }
        admin.setAccountAttivo(false);
        return adminRepository.save(admin);
    }

    public Admin riattiva(UUID id) {
        Admin admin = trovaPerId(id);
        admin.setAccountAttivo(true);
        return adminRepository.save(admin);
    }

}
