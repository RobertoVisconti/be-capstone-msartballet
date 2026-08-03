package robertovisconti.be_capstone_msartballet.runners;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import robertovisconti.be_capstone_msartballet.entities.Admin;
import robertovisconti.be_capstone_msartballet.repositories.utenti.AdminRepository;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;

import java.time.LocalDate;

@Component
public class AdminRunner implements ApplicationRunner {

    private final UtenteRepository utenteRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminEmail;
    private final String adminPassword;

    public AdminRunner(UtenteRepository utenteRepository,
                       AdminRepository adminRepository,
                       PasswordEncoder passwordEncoder,
                       @Value("${admin.email}") String adminEmail,
                       @Value("${admin.password}") String adminPassword) {
        this.utenteRepository = utenteRepository;
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (utenteRepository.existsByEmail(adminEmail)) {
            return;
        }
        Admin admin = new Admin("Admin", "Sistema", adminEmail, passwordEncoder.encode(adminPassword), LocalDate.now());
        adminRepository.save(admin);
        System.out.println("Admin di default creato con email: " + adminEmail);
    }
}
