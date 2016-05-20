package com.ghb.coltpath.admin.controller;

import com.ghb.coltpath.admin.dto.StudentPost;
import com.ghb.coltpath.admin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by agheboianu on 20.05.2016.
 */
@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentService studentService;

    @RequestMapping("")
    public String showStudents(){
        return "students";
    }

    @RequestMapping("/register")
    public String showRegistration(Model model) {
        model.addAttribute("studentPost", new StudentPost());
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String createUser(@ModelAttribute("studentPost") @Validated StudentPost studentPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        studentService.registerStudent(studentPost);
        return "redirect:/student";
    }
}
