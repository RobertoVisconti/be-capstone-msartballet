package robertovisconti.be_capstone_msartballet.validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AcquistoValidator.class)
@Documented
public @interface AcquistoValido {

    String message() default "Specificare esattamente uno tra prodotto, corso o sala da acquistare";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
