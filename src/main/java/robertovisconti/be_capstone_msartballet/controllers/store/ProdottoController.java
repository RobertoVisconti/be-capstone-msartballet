package robertovisconti.be_capstone_msartballet.controllers.store;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_capstone_msartballet.entities.Prodotto;
import robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO.NewProdottoDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.storeDTO.ProdottoRespDTO;
import robertovisconti.be_capstone_msartballet.services.store.ProdottoService;
import robertovisconti.be_capstone_msartballet.tools.CloudinaryUploaderService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    private final ProdottoService prodottoService;
    private final CloudinaryUploaderService cloudinaryUploaderService;

    public ProdottoController(ProdottoService prodottoService, CloudinaryUploaderService cloudinaryUploaderService) {
        this.prodottoService = prodottoService;
        this.cloudinaryUploaderService = cloudinaryUploaderService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdottoRespDTO creaProdotto(@RequestBody @Valid NewProdottoDTO body) {
        return mappa(prodottoService.creaProdotto(body));
    }

    @GetMapping
    public Page<ProdottoRespDTO> trovaTutti(@PageableDefault(size = 20) Pageable pageable) {
        return prodottoService.trovaTutti(pageable).map(this::mappa);
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

    @PostMapping(value = "/{id}/immagine", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ProdottoRespDTO caricaImmagine(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        return mappa(prodottoService.caricaImmagine(id, file));
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
