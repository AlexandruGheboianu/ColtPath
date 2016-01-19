package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.PasswordChangePost;
import com.ghb.coltpath.dto.RegisterUserPost;
import com.ghb.coltpath.model.Role;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.RoleRepository;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Ghebo on 1/16/2016.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void registerUser(RegisterUserPost registerUserPost) {

        User user = new User();
        user.setLogin(registerUserPost.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(registerUserPost.getPassword()));
        user.setEmail(registerUserPost.getEmail());
        user.setConfirmationUrl("/users/confirm?token=" + UUID.randomUUID().toString());
        user.setFirstName(registerUserPost.getFirstName());
        user.setLastName(registerUserPost.getLastName());

        Role role = roleRepository.findOneByName("STUDENT");
        if (role == null) {
            role = new Role();
            role.setName("STUDENT");
            role = roleRepository.save(role);
        }
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public void changePassword(String login, PasswordChangePost passwordChangePost) {
        User user = userRepository.findOneByLogin(login);
        user.setPassword(new BCryptPasswordEncoder().encode(passwordChangePost.getNewPassword()));
        userRepository.save(user);
    }

    public User getUser(String login) {
        return userRepository.findOneByLogin(login);
    }
}
