package com.ghb.coltpath.validators;

/**
 * Created by Ghebo on 1/16/2016.
 */

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validation annotation to validate that a user is present in the database.
 * <p/>
 * <p/>
 * Example, check if user is not present:
 *
 * @UserExists(email="email", exists = false, message="The user is already present")
 * <p/>
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UserExistsValidator.class)
@Documented
public @interface UserExists {
    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    boolean exists();
}
