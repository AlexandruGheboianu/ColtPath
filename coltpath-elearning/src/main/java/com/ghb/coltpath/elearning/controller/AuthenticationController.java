package com.ghb.coltpath.elearning.controller;

import com.ghb.coltpath.elearning.dto.writer.PasswordChangePost;
import com.ghb.coltpath.elearning.service.StudentService;
import com.ghb.coltpath.elearning.state.StudentStatePersist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by Ghebo on 1/15/2016.
 */
@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentStatePersist studentStatePersist;


    @RequestMapping("/login")
    public String showLogin() {
        return "public/login";
    }

    @RequestMapping(value = "/pwd_change", method = RequestMethod.POST)
    String changePassword(@ModelAttribute("passwordChange") @Validated PasswordChangePost passwordChangePost, Authentication authentication) {
        studentService.changePassword(authentication.getName(), passwordChangePost);
        return "redirect:/user";
    }

}
