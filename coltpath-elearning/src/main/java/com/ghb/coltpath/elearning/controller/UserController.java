package com.ghb.coltpath.elearning.controller;

import com.ghb.coltpath.core.model.User;
import com.ghb.coltpath.elearning.dto.writer.PasswordChangePost;
import com.ghb.coltpath.elearning.dto.writer.UserPost;
import com.ghb.coltpath.elearning.service.StudentService;
import com.ghb.coltpath.elearning.state.StudentStatePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    StudentService studentService;

    @Autowired
    StudentStatePersist studentStatePersist;


    @RequestMapping("/login")
    public String showLogin() {
        return "public/login";
    }

    @RequestMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("userPost", new UserPost());
        return "public/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("userPost") @Validated UserPost userPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "public/register";
        }
        studentService.registerUser(userPost);
        return "redirect:/login";
    }

    @RequestMapping(value = "/user/pwd_change", method = RequestMethod.POST)
    String changePassword(@ModelAttribute("passwordChange") @Validated PasswordChangePost passwordChangePost, Authentication authentication) {
        studentService.changePassword(authentication.getName(), passwordChangePost);
        return "redirect:/user";
    }


    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String showUser(Authentication authentication, Model model) {
        User user = studentService.getUser(authentication.getName());
        model.addAttribute("user", user);
        return "user";
    }


}
