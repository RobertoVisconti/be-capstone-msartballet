package robertovisconti.be_capstone_msartballet.services.store;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Prodotto;
import robertovisconti.be_capstone_msartballet.exceptions.NotFoundException;
import robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO.NewProdottoDTO;
import robertovisconti.be_capstone_msartballet.repositories.store.ProdottoRepository;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import java.util.List;
import java.util.UUID;

@Service
public class ProdottoService {

    private final ProdottoRepository prodottoRepository;
    private final CloudinaryUploaderService cloudinaryUploaderService;

    public ProdottoService(ProdottoRepository prodottoRepository, CloudinaryUploaderService cloudinaryUploaderService) {
        this.prodottoRepository = prodottoRepository;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    public Prodotto creaProdotto(NewProdottoDTO body) {
        Prodotto nuovoProdotto = new Prodotto(body.titolo(), body.descrizioneProdotto(), body.imgProdotto(), body.prezzoProdotto());
        return prodottoRepository.save(nuovoProdotto);
    }

    public Prodotto caricaImmagine(UUID id, MultipartFile file) {
        Prodotto prodotto = trovaPerId(id);
        String url = cloudinaryUploaderService.caricaImmagine(file);
        prodotto.setImgProdotto(url);
        return prodottoRepository.save(prodotto);
    }

    public List<Prodotto> trovaTutti() {
        return prodottoRepository.findAll();
    }

    public Prodotto trovaPerId(UUID id) {
        return prodottoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Nessun prodotto trovato con id " + id));
    }

    public Prodotto modificaProdotto(UUID id, NewProdottoDTO body) {
        Prodotto prodotto = trovaPerId(id);
        prodotto.setTitolo(body.titolo());
        prodotto.setDescrizioneProdotto(body.descrizioneProdotto());
        prodotto.setImgProdotto(body.imgProdotto());
        prodotto.setPrezzoProdotto(body.prezzoProdotto());
        return prodottoRepository.save(prodotto);
    }

    public void eliminaProdotto(UUID id) {
        prodottoRepository.delete(trovaPerId(id));
    }
}
