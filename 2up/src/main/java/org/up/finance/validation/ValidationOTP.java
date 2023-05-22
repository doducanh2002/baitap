package org.up.finance.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static org.up.finance.constant.FinanceConstant.ValidationMessage.INVALID_OTP;

@Constraint(validatedBy = ValidationOTP.OTPValidator.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
public @interface ValidationOTP {

  String message() default INVALID_OTP;

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class OTPValidator implements ConstraintValidator<ValidationOTP, String> {

    @Override
    public void initialize(ValidationOTP constraintAnnotation) {
      ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String otp, ConstraintValidatorContext constraintValidatorContext) {
      String otpRegex = "^\\d{4}$";
      return otp.matches(otpRegex);
    }
  }
}
