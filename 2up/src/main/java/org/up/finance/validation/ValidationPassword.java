package org.up.finance.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static org.up.finance.constant.FinanceConstant.ValidationMessage.INVALID_PASSWORD;

@Constraint(validatedBy = ValidationPassword.PasswordValidation.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface ValidationPassword {
  String message() default INVALID_PASSWORD;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class PasswordValidation implements ConstraintValidator<ValidationPassword, String> {

    @Override
    public void initialize(ValidationPassword constraintAnnotation) {
      ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
      String passwordRegex = "^(?=.{8,16})(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&*()+=|?_:;<>\\-\\[\\]\"'!{},.]).*$";
      return password.matches(passwordRegex);
    }
  }
}
