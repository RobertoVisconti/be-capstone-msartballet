package robertovisconti.be_capstone_msartballet.controllers.corso;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_capstone_msartballet.entities.Disciplina;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.DisciplinaRespDTO;
import robertovisconti.be_capstone_msartballet.payloadsDTO.corsoDTO.NewDisciplinaDTO;
import robertovisconti.be_capstone_msartballet.services.corso.DisciplinaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.util.UUID;

@RestController
@RequestMapping("/discipline")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public DisciplinaRespDTO creaDisciplina(@RequestBody @Valid NewDisciplinaDTO body) {
        Disciplina nuovaDisciplina = disciplinaService.creaDisciplina(body);
        return mappaDisciplina(nuovaDisciplina);
    }

    @GetMapping
    public Page<DisciplinaRespDTO> trovaTutte(@PageableDefault(size = 20) Pageable pageable) {
        return disciplinaService.trovaTutte(pageable).map(this::mappaDisciplina);
    }

    @GetMapping("/{id}")
    public DisciplinaRespDTO trovaPerId(@PathVariable UUID id) {
        return mappaDisciplina(disciplinaService.trovaPerId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public DisciplinaRespDTO modificaDisciplina(@PathVariable UUID id, @RequestBody @Valid NewDisciplinaDTO body) {
        Disciplina disciplinaModificata = disciplinaService.modificaDisciplina(id, body);
        return mappaDisciplina(disciplinaModificata);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaDisciplina(@PathVariable UUID id) {
        disciplinaService.eliminaDisciplina(id);
    }

    private DisciplinaRespDTO mappaDisciplina(Disciplina disciplina) {
        return new DisciplinaRespDTO(
                disciplina.getId(),
                disciplina.getNome(),
                disciplina.getDescrizione()
        );
    }
}
