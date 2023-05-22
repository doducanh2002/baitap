package org.up.finance.validation;


import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.beans.BeanWrapperImpl;

import java.lang.annotation.*;

import static org.up.finance.constant.FinanceConstant.ValidationMessage.CONFIRM_PASSWORD_NOT_MATCH;


@Constraint(validatedBy = ValidationConfirmPassword.ConfirmedFieldValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface ValidationConfirmPassword {

  String message() default CONFIRM_PASSWORD_NOT_MATCH;

  String originalField();

  String confirmationField();

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target({ElementType.TYPE})
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    ValidationConfirmPassword[] value();
  }

  class ConfirmedFieldValidator implements ConstraintValidator<ValidationConfirmPassword, Object> {
    private String originalField;
    private String confirmField;
    private String message;

    @Override
    public void initialize(ValidationConfirmPassword constraintAnnotation) {
      this.originalField = constraintAnnotation.originalField();
      this.confirmField = constraintAnnotation.confirmationField();
      this.message = constraintAnnotation.message();
      ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

      Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(originalField);
      Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(confirmField);

      boolean isValid = fieldValue != null && fieldValue.equals(fieldMatchValue);

      if (!isValid) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext
              .buildConstraintViolationWithTemplate(message)
              .addPropertyNode(confirmField)
              .addConstraintViolation();
      }

      return isValid;
    }
  }

}
