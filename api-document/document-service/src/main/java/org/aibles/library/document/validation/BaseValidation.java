package org.aibles.library.document.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class BaseValidation<T> {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory()
            .getValidator();

    public Map<String, Object> isValid() {
        var violations = VALIDATOR.validate(this);
        if (!violations.isEmpty()) {
            log.info("(isValid){}: {}", this.getClass().getTypeName());
            for (var violation: violations) {
                log.error("(isValid){}: {}", getField(violation), violation.getMessage());
            }
        }
        return new HashMap<>();
    }

    public void validate() {
        var errorMap = new HashMap<String, Object>();

        var violations = VALIDATOR.validate(this);
        if (!violations.isEmpty()) {
            log.info("(isValid){} --> INVALID", this.getClass().getTypeName());
            for (var violation: violations) {
                errorMap.put(getField(violation), violation.getMessage());
            }
        }

        if (!errorMap.isEmpty()) {
            System.out.println(errorMap);
            throw new RuntimeException("invalid entity");
        }
    }

    private String getField(ConstraintViolation<BaseValidation<T>> violation) {
        return violation.getPropertyPath().toString();
    }

}