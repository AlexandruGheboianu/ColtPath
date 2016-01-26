package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.ProjectPost;
import com.ghb.coltpath.model.Project;
import com.ghb.coltpath.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by agheboianu on 25.01.2016.
 */
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project createProject(ProjectPost projectPost) {
        Project project = new Project();
        project.setName(projectPost.getName());
        return projectRepository.save(project);
    }

    @Override
    public Project getProject(Long id) {
        return projectRepository.findOne(id);
    }

    @Override
    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
}
