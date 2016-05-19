package com.ghb.coltpath.elearning.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by agheboianu on 22.01.2016.
 */

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = UserIdExistsValidator.class)
@Documented
public @interface UserIdExists {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    boolean exists();
}
