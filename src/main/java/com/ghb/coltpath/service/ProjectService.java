package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.ProjectPost;
import com.ghb.coltpath.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by agheboianu on 25.01.2016.
 */
public interface ProjectService {
    @PreAuthorize("hasAuthority('ADMIN')")
    Project createProject(ProjectPost projectPost);

    @PreAuthorize("hasAuthority('ADMIN')")
    Project getProject(Long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    Page<Project> getProjects(Pageable pageable);
}
