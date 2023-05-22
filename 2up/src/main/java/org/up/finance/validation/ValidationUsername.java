package org.up.finance.validation;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static org.up.finance.constant.FinanceConstant.ValidationMessage.INVALID_USERNAME;

@Constraint(validatedBy = ValidationUsername.UsernameValidation.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface ValidationUsername {

  String message() default INVALID_USERNAME;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class UsernameValidation implements ConstraintValidator<ValidationUsername, String> {

    @Override
    public void initialize(ValidationUsername constraintAnnotation) {
      ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
      String usernameRegex = "[A-Za-z0-9]+";
      return username.matches(usernameRegex);
    }
  }
}
