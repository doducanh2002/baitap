package org.up.finance.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Constraint(validatedBy = ValidateEnum.EnumValidation.class)
public @interface ValidateEnum {

  Class<? extends Enum<?>> enumClazz();

  String message() default "Invalid enum value";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  class EnumValidation implements ConstraintValidator<ValidateEnum, String> {

    private Set<String> enumValue;

    @Override
    public void initialize(ValidateEnum constraintAnnotation) {
      enumValue = Arrays.stream(constraintAnnotation.enumClazz().getEnumConstants())
          .map(Enum::name)
          .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String enumString, ConstraintValidatorContext constraintValidatorContext) {
      return enumValue.contains(enumString);
    }
  }

}
