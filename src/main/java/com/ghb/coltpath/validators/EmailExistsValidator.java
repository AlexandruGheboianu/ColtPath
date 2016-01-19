package com.ghb.coltpath.validators;

import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Ghebo on 1/19/2016.
 */
public class EmailExistsValidator implements ConstraintValidator<EmailExists, Object> {
    @Autowired
    UserRepository userRepository;

    private boolean exists;

    @Override
    public void initialize(final EmailExists constraintAnnotation) {
        exists = constraintAnnotation.exists();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            String email = (String) value;

            return (userRepository.findOneByEmail(email) != null) == exists;
        } catch (final Exception ignore) {
            // ignore
        }
        return true;
    }
}
