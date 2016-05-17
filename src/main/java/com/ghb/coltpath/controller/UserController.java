package com.ghb.coltpath.controller;

import com.ghb.coltpath.Application;
import com.ghb.coltpath.dto.writer.PasswordChangePost;
import com.ghb.coltpath.dto.writer.UserPost;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.service.UserService;
import com.ghb.coltpath.state.Persist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.CompositeStateMachineListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Ghebo on 1/15/2016.
 */
@Controller
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    Persist persist;


    @RequestMapping("/login")
    public String showLogin() {
        return "public/login";
    }

    @RequestMapping("/register")
    public String showRegistration(Model model) {
        persist.change(1,"START");
        persist.change(1,"PASS");
        model.addAttribute("userPost", new UserPost());
        return "public/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userPost") @Validated UserPost userPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "public/register";
        }
        userService.registerUser(userPost);
        return "redirect:/login";
    }

    @RequestMapping(value = "/user/pwd_change", method = RequestMethod.POST)
    String changePassword(@ModelAttribute("passwordChange") @Validated PasswordChangePost passwordChangePost, Authentication authentication) {
        userService.changePassword(authentication.getName(), passwordChangePost);
        return "redirect:/user";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser(Authentication authentication, Model model) {
        User user = userService.getUser(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }


}
