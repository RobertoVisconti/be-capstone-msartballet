package robertovisconti.be_capstone_msartballet.services.utenti;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Utente;
import robertovisconti.be_capstone_msartballet.exceptions.BadRequestException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.utenteDTO.CambiaPasswordDTO;
import robertovisconti.be_capstone_msartballet.repositories.utenti.UtenteRepository;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

@Service
public class UtenteService implements UserDetailsService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final CloudinaryUploaderService cloudinaryUploaderService;


    public UtenteService(UtenteRepository utenteRepository, PasswordEncoder passwordEncoder, CloudinaryUploaderService cloudinaryUploaderService) {
        this.utenteRepository = utenteRepository;
        this.passwordEncoder = passwordEncoder;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Nessun utente trovato con email " + email));
    }

    public String aggiornaImgProfilo(Utente utente, MultipartFile file) {
        String url = cloudinaryUploaderService.caricaImmagine(file);
        utente.setImgProfilo(url);
        utenteRepository.save(utente);
        return url;
    }

    public void cambiaPassword(Utente utente, CambiaPasswordDTO body) {
        if (!passwordEncoder.matches(body.vecchiaPassword(), utente.getPassword())) {
            throw new BadRequestException("La vecchia password non è corretta");
        }
        utente.setPassword(passwordEncoder.encode(body.nuovaPassword()));
        utenteRepository.save(utente);
    }

}
