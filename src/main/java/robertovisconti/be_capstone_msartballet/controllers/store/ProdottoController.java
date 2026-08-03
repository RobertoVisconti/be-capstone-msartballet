package robertovisconti.be_capstone_msartballet.controllers.store;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Prodotto;
import robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO.NewProdottoDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO.ProdottoRespDTO;
import robertovisconti.be_capstone_msartballet.services.store.ProdottoService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {
    private final ProdottoService prodottoService;

    public ProdottoController(ProdottoService prodottoService) {
        this.prodottoService = prodottoService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoRespDTO creaProdotto(@RequestBody @Valid NewProdottoDTO body) {
        return mappa(prodottoService.creaProdotto(body));
    }

    @GetMapping
    public List<ProdottoRespDTO> trovaTutti() {
        return prodottoService.trovaTutti().stream().map(this::mappa).toList();
    }

    @GetMapping("/{id}")
    public ProdottoRespDTO trovaPerId(@PathVariable UUID id) {
        return mappa(prodottoService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ProdottoRespDTO modificaProdotto(@PathVariable UUID id, @RequestBody @Valid NewProdottoDTO body) {
        return mappa(prodottoService.modificaProdotto(id, body));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaProdotto(@PathVariable UUID id) {
        prodottoService.eliminaProdotto(id);
    }

    private ProdottoRespDTO mappa(Prodotto prodotto) {
        return new ProdottoRespDTO(prodotto.getId(), prodotto.getTitolo(), prodotto.getDescrizioneProdotto(), prodotto.getImgProdotto(), prodotto.getPrezzoProdotto());
    }
}
