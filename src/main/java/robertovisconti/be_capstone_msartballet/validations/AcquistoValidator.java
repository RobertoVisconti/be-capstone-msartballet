package robertovisconti.be_capstone_msartballet.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import robertovisconti.be_capstone_msartballet.payloadsDTO.pagamentoDTO.NewTransazioneDTO;

public class AcquistoValidator implements ConstraintValidator<AcquistoValido, NewTransazioneDTO> {

    @Override
    public boolean isValid(NewTransazioneDTO dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }
        int scelti = 0;
        if (dto.idProdotto() != null) scelti++;
        if (dto.idCorso() != null) scelti++;
        if (dto.idSala() != null) scelti++;

        return scelti == 1;
    }
}
