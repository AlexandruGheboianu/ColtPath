package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.PasswordChangePost;
import com.ghb.coltpath.dto.RegisterUserPost;
import com.ghb.coltpath.dto.RequestOutcomeMessage;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Created by Ghebo on 1/15/2016.
 */
@RestController
public class UserResource {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/pwd_change", method = RequestMethod.POST)
    public
    @ResponseBody
    RequestOutcomeMessage changePassword(@Validated @RequestBody PasswordChangePost passwordChangePost, Principal principal) {
        userService.changePassword(principal.getName(), passwordChangePost);
        return new RequestOutcomeMessage("Password changed successfully");
    }

    @RequestMapping(value = "/users/{login}", method = RequestMethod.GET)
    public
    @ResponseBody
    User showUser(@PathVariable String login, Principal principal) {
        if (!principal.getName().equals(login)) {
            throw new ForbiddenException("You don't have permission to see this user.");
        }

        return userService.getUser(login);
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public
    @ResponseBody
    RequestOutcomeMessage createUser(@Validated @RequestBody RegisterUserPost registerUserPost) {
        userService.registerUser(registerUserPost);
        return new RequestOutcomeMessage("User created successfuly");
    }
}
