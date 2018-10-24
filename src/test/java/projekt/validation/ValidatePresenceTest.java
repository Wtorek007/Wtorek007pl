package projekt.validation;

import org.junit.jupiter.api.Test;
import projekt.core.validation.ValidatePresence;
import projekt.core.validation.ValidationEngine;
import projekt.core.validation.ValidationErrors;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class ValidatePresenceTest {

    private class Bean {

        @ValidatePresence
        String field;

        @ValidatePresence
        String otherField = "value";

    }

    private ValidationEngine engine = new ValidationEngine();

    @Test
    public void validatesPresenceOfStrings() {
        Bean bean = new Bean();

        ValidationErrors errors = engine.validate(bean);

        assertThat(errors.isInvalid()).isTrue();
    }

    @Test
    public void savesPresenceErrorMessage() {
        Bean bean = new Bean();

        ValidationErrors errors = engine.validate(bean);

        assertThat(errors.getErrors("field")).containsExactly("can't be blank");
    }

    @Test
    public void doesNotReturnErrorsForValidFields() {
        Bean bean = new Bean();

        ValidationErrors errors = engine.validate(bean);

        assertThat(errors.getErrors("otherField")).isEmpty();
    }

    @Test
    public void doesNotReturnAnyErrorsWhenObjectValid() {
        Bean bean = new Bean();
        bean.field = "value";

        ValidationErrors errors = engine.validate(bean);

        assertThat(errors.isValid()).isTrue();
    }
}
