package com.encom.bookstore.constraints;

import com.encom.bookstore.validators.UniqueUserLoginValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueUserLoginValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUserLogin {
    String message() default "Login is already used";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
