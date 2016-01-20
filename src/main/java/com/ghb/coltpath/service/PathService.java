package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.reader.PathPost;
import com.ghb.coltpath.model.Path;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Created by Ghebo on 1/20/2016.
 */
public interface PathService {
    @PreAuthorize("hasAuthority('ADMIN') or authentication.principal.paths.contains(#id)")
    Path getPath(long id);

    @PreAuthorize("hasAuthority('ADMIN')")
    Path createPath(PathPost pathPost);
}
