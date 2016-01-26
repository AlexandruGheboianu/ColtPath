package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.reader.UserGet;
import com.ghb.coltpath.dto.writer.PasswordChangePost;
import com.ghb.coltpath.dto.writer.UserPost;
import com.ghb.coltpath.dto.writer.RequestOutcomeMessage;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.service.UserService;
import com.ghb.coltpath.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by Ghebo on 1/15/2016.
 */
@RestController
public class UserResource {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/users/{login}/pwd_change", method = RequestMethod.POST)
    public
    @ResponseBody
    RequestOutcomeMessage changePassword(@PathVariable String login, @Validated @RequestBody PasswordChangePost
            passwordChangePost) {
        userService.changePassword(login, passwordChangePost);
        return new RequestOutcomeMessage("Password changed successfully");
    }


    @RequestMapping(value = "/users/{login}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserGet showUser(@PathVariable String login) {
        User user = userService.getUser(login);
        UserGet response = new UserGet(user.getLogin(), user.getEmail(), user.getFirstName(), user.getLastName(), user.isActive(),
                user.getLastLogin(), user.getConfirmationUrl());

        response.add(linkTo(methodOn(UserResource.class).showUser(login)).withSelfRel());
        response.add(linkTo(methodOn(UserResource.class).changePassword(login, null)).withRel("change_password"));
        return response;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public
    @ResponseBody
    RequestOutcomeMessage createUser(@Validated @RequestBody UserPost userPost) {
        userService.registerUser(userPost);
        return new RequestOutcomeMessage("User created successfuly");
    }


}
