package robertovisconti.be_capstone_msartballet.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntervelloOrarioValidator.class)
@Documented
public @interface IntervalloOrarioValido {

    String message() default "L'orario di fine deve essere successivo all'orario di inizio!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String campoInizio();

    String campoFine();
}
