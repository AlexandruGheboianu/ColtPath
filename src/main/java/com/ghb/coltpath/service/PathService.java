package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.PathPost;
import com.ghb.coltpath.model.Path;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

/**
 * Created by Ghebo on 1/20/2016.
 */
public interface PathService {
    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.paths.contains(#id)")
    Path getPath(long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    Path createPath(PathPost pathPost);

    List<Path> getPaths();

    @PreAuthorize("hasAuthority('ADMIN')")
    boolean addUserToPath(long id, Set<Long> userIds);
}
