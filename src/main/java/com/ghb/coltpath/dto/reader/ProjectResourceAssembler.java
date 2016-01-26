package com.ghb.coltpath.dto.reader;

import com.ghb.coltpath.model.Project;
import com.ghb.coltpath.rest.PathResource;
import com.ghb.coltpath.rest.ProjectResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by agheboianu on 25.01.2016.
 */
public class ProjectResourceAssembler extends ResourceAssemblerSupport<Project, Resource> {
    public ProjectResourceAssembler() {
        super(ProjectResource.class, Resource.class);
    }


    @Override
    public List<Resource> toResources(Iterable<? extends Project> projects) {
        List<Resource> resources = new ArrayList<Resource>();
        for (Project project : projects) {
            ProjectGet projectGet = new ProjectGet(project.getName(), project.getCreatedBy(), project.getCreationDate());
            resources.add(new Resource<ProjectGet>(projectGet, linkTo(methodOn(ProjectResource.class).showProject
                    (project
                            .getId())).withSelfRel()));
        }
        return resources;
    }

    @Override
    public Resource toResource(Project project) {
        ProjectGet projectGet = new ProjectGet(project.getName(), project.getCreatedBy(), project.getCreationDate());
        return new Resource<ProjectGet>(projectGet, linkTo(methodOn(ProjectResource.class).showProject
                (project
                        .getId())).withSelfRel());
    }
}
