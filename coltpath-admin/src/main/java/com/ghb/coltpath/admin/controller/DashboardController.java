package com.ghb.coltpath.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by agheboianu on 20.05.2016.
 */
@Controller
public class DashboardController {

    @RequestMapping("/")
    public String showDashboard(Model model) {
        return "dashboard";
    }
}
