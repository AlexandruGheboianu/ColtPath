package com.ghb.coltpath.controller;

import com.ghb.coltpath.dto.writer.ProjectPost;
import com.ghb.coltpath.model.Project;
import com.ghb.coltpath.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created by agheboianu on 25.01.2016.
 */
@Controller
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String addProject(@ModelAttribute("project") @Validated ProjectPost projectPost) {
        Project project = projectService.createProject(projectPost);
        return "redirect:/projects";
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    void showProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String showAllProjects(Model model, @RequestParam int page, @RequestParam int size) {
        Page<Project> projects = projectService.getProjects(new PageRequest(page, size));
        return "projects";
    }
}
