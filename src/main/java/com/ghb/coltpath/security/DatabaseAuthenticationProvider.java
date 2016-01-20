package com.ghb.coltpath.security;

import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.model.Role;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.UserRepository;
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
import java.util.Set;

/**
 * Created by Ghebo on 1/6/2016.
 */
@Service
public class DatabaseAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserRepository userRepository;

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

        User user = userRepository.findOneByLogin(userName);
        Set<Path> paths = user.getPaths();
        if (user == null) {
            this.logger.warn("Username {}: user not found", userName);
            valid = false;
        } else {
            if (!user.isActive()) {
                this.logger.warn("Username {}: not active", userName);
                valid = false;
            }
            // Check password
            if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
                this.logger.warn("Username {}: bad password entered", userName);
                valid = false;
            }
        }

        if (!valid) {
            throw new BadCredentialsException("Invalid Username/Password for user " + userName);
        }
        user.setLastLogin(new Date());
        userRepository.save(user);

        final Set<Role> roles = user.getRoles();
        final List<GrantedAuthority> auths = !roles.isEmpty() ? AuthorityUtils.commaSeparatedStringToAuthorityList(user.serializedRoles()) : AuthorityUtils.NO_AUTHORITIES;
        // enabled, account not expired, credentials not expired, account not locked
        com.ghb.coltpath.security.UserDetails userDetails = new com.ghb.coltpath.security.UserDetails(userName, password, true, true, true, true, auths);

        for (Path path : paths) {
            userDetails.getPaths().add(path.getId());
        }
        return userDetails;
    }
}
