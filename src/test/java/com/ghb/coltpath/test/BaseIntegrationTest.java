package com.ghb.coltpath.test;

import com.ghb.coltpath.model.Role;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.RoleRepository;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by Ghebo on 1/16/2016.
 */
public abstract class BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;


    protected User createUser() {
        User user = new User();
        user.setLogin("awesome");
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        user.setActive(true);

        return userRepository.save(user);
    }

    public User addRole(User user, String roleName) {
        Role role = roleRepository.findOneByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        user.getRoles().add(role);
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
