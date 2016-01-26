package com.ghb.coltpath.validators;

import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by agheboianu on 22.01.2016.
 */
public class UserIdExistsValidator implements ConstraintValidator<UserIdExists, Object> {
    @Autowired
    UserRepository userRepository;

    private boolean exists;

    @Override
    public void initialize(final UserIdExists constraintAnnotation) {
        exists = constraintAnnotation.exists();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            if (value instanceof Long) {
                return userRepository.exists((Long) value) == exists;
            }
            if (value instanceof Iterable) {
                for (Object obj : (Iterable) value) {
                    if (userRepository.exists((Long) obj) != exists) {
                        return false;
                    }
                }
                return true;
            }

        } catch (final Exception ignore) {
            ignore.printStackTrace();
        }
        return false;
    }
}
