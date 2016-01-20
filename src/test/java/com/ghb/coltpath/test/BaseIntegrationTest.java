package com.ghb.coltpath.test;

import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Ghebo on 1/16/2016.
 */
public abstract class BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;


    protected User createUser() {
        User user = new User();
        user.setLogin("awesome");
        user.setEmail("test@example.com");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        user.setActive(true);
        return userRepository.save(user);
    }

    protected User createUser(String login, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setActive(true);
        return userRepository.save(user);
    }

    protected User createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }


}
