package com.ghb.coltpath.elearning.security;

import com.ghb.coltpath.core.model.User;
import com.ghb.coltpath.elearning.model.Student;
import com.ghb.coltpath.elearning.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Ghebo on 1/6/2016.
 */
@Service
public class DatabaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StudentRepository studentRepository;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String userName, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        boolean valid = true;

        final String password = (String) usernamePasswordAuthenticationToken.getCredentials();
        if (!StringUtils.hasText(password)) {
            this.logger.warn("Username {}: no password provided", userName);
            valid = false;
        }

        Student student = studentRepository.findOneByLogin(userName);

        if (student == null) {
            this.logger.warn("Username {}: student not found", userName);
            valid = false;
        } else {
            if (!student.isActive()) {
                this.logger.warn("Username {}: not active", userName);
                valid = false;
            }
            // Check password
            if (!new BCryptPasswordEncoder().matches(password, student.getPassword())) {
                this.logger.warn("Username {}: bad password entered", userName);
                valid = false;
            }
        }

        if (!valid) {
            throw new BadCredentialsException("Invalid Username/Password for student " + userName);
        }
        student.setLastLogin(new Date());
        studentRepository.save(student);


        final List<GrantedAuthority> auths = AuthorityUtils.NO_AUTHORITIES;
        // enabled, account not expired, credentials not expired, account not locked
        com.ghb.coltpath.elearning.security.UserDetails userDetails = new com.ghb.coltpath.elearning.security.UserDetails(userName, password, true, true, true, true, auths);


        return userDetails;
    }
}
