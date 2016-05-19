package com.ghb.coltpath.elearning.validators;

import com.ghb.coltpath.elearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Ghebo on 1/16/2016.
 */
public class UserExistsValidator implements ConstraintValidator<UserExists, Object> {
    @Autowired
    StudentRepository studentRepository;

    private boolean exists;

    @Override
    public void initialize(final UserExists constraintAnnotation) {
        exists = constraintAnnotation.exists();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            String login = (String) value;

            return (studentRepository.findOneByLogin(login) != null) == exists;
        } catch (final Exception ignore) {
            // ignore
        }
        return false;
    }
}
