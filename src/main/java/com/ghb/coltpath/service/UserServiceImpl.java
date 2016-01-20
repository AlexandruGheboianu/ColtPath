package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.PasswordChangePost;
import com.ghb.coltpath.dto.writer.UserPost;
import com.ghb.coltpath.model.Role;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.RoleRepository;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Ghebo on 1/16/2016.
 */
@Service
public class UserServiceImpl implements  UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    public void registerUser(UserPost userPost) {
        User user = new User();
        user.setLogin(userPost.getLogin());
        user.setPassword(new BCryptPasswordEncoder().encode(userPost.getPassword()));
        user.setEmail(userPost.getEmail());
        user.setConfirmationUrl("/users/confirm?token=" + UUID.randomUUID().toString());
        user.setFirstName(userPost.getFirstName());
        user.setLastName(userPost.getLastName());

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
