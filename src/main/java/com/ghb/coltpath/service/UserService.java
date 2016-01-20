package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.PasswordChangePost;
import com.ghb.coltpath.dto.writer.UserPost;
import com.ghb.coltpath.model.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by agheboianu on 20.01.2016.
 */
public interface UserService {
    @PreAuthorize("hasAuthority('ADMIN')")
    void registerUser(UserPost userPost);

    @PreAuthorize("#login == authentication.name")
    void changePassword(String login, PasswordChangePost passwordChangePost);

    @PreAuthorize("#login == authentication.name")
    User getUser(String login);
}
