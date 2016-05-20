package com.ghb.coltpath.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by agheboianu on 20.05.2016.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping("/login")
    public String showLogin(@RequestParam(name = "failed", required = false, defaultValue = "false") boolean failed, Model model) {
        model.addAttribute("failed", failed);
        return "public/login";
    }
}
