package robertovisconti.be_capstone_msartballet.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class IntervelloOrarioValidator implements ConstraintValidator<IntervalloOrarioValido, Object> {

    private String campoInizio;
    private String campoFine;

    @Override
    public void initialize(IntervalloOrarioValido annotazione){
        this.campoInizio = annotazione.campoInizio();
        this.campoFine = annotazione.campoFine();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context){
        if(dto == null){
            return true;
        }
        BeanWrapperImpl wrapper = new BeanWrapperImpl(dto);
        Object inizio = wrapper.getPropertyValue(campoInizio);
        Object fine = wrapper.getPropertyValue(campoFine);

        if(inizio == null || fine == null){
            return true;
        }
        if (!(inizio instanceof Comparable) || !(fine instanceof Comparable)){
            return true;
        }

        @SuppressWarnings("u")
    }




}
