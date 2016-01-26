package com.ghb.coltpath.rest;

import com.ghb.coltpath.dto.reader.ProjectGet;
import com.ghb.coltpath.dto.reader.ProjectResourceAssembler;
import com.ghb.coltpath.dto.writer.ProjectPost;
import com.ghb.coltpath.dto.writer.RequestOutcomeMessage;
import com.ghb.coltpath.model.Project;
import com.ghb.coltpath.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * Created by agheboianu on 25.01.2016.
 */
@RestController
public class ProjectResource {
    private static final ProjectResourceAssembler PROJECT_RESOURCE_ASSEMBLER = new ProjectResourceAssembler();

    @Autowired
    ProjectService projectService;

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public
    @ResponseBody
    Resource<ProjectGet> addProject(@Validated @RequestBody ProjectPost projectPost) {
        Project project = projectService.createProject(projectPost);

        return PROJECT_RESOURCE_ASSEMBLER.toResource(project);
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Resource<ProjectGet> showProject(@PathVariable Long id) {
        Project project = projectService.getProject(id);

        return PROJECT_RESOURCE_ASSEMBLER.toResource(project);
    }

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity showAllProjects(Pageable pageable, PagedResourcesAssembler assembler) {
        if (pageable.getPageSize() > 100) {
            return new ResponseEntity<RequestOutcomeMessage>(new RequestOutcomeMessage("Page size over 100 not " +
                    "permitted" +
                    "."),
                    HttpStatus.BAD_REQUEST);
        }
        Page<Project> projects = projectService.getProjects(pageable);

        return new ResponseEntity<PagedResources<ProjectGet>>(assembler.toResource(projects, new
                ProjectResourceAssembler
                ()), HttpStatus.OK);
    }
}
