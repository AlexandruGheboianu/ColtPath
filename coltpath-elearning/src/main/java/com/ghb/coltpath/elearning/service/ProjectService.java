package com.ghb.coltpath.elearning.service;

import com.ghb.coltpath.elearning.dto.writer.ProjectPost;
import com.ghb.coltpath.core.model.Project;
import com.ghb.coltpath.elearning.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Service
public class ProjectService  {
    @Autowired
    ProjectRepository projectRepository;

    public Project createProject(ProjectPost projectPost) {
        Project project = new Project();
        project.setName(projectPost.getName());
        return projectRepository.save(project);
    }

    public Project getProject(Long id) {
        return projectRepository.findOne(id);
    }

    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
}
