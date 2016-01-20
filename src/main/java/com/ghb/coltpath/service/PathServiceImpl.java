package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.reader.PathPost;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.repository.PathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Ghebo on 1/20/2016.
 */
@Service
public class PathServiceImpl implements PathService {
    @Autowired
    PathRepository pathRepository;

    @Override
    public Path getPath(long id) {
        return pathRepository.findOne(id);
    }

    @Override
    public Path createPath(PathPost pathPost) {
        return null;
    }
}
