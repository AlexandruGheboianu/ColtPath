package com.ghb.coltpath.service;

import com.ghb.coltpath.dto.writer.PathPost;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.PathRepository;
import com.ghb.coltpath.repository.UserRepository;
import com.ghb.coltpath.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by Ghebo on 1/20/2016.
 */
@Service
public class PathServiceImpl implements PathService {
    @Autowired
    PathRepository pathRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Path getPath(long id) {
        return pathRepository.findOne(id);
    }

    @Override
    public Path createPath(PathPost pathPost) {
        Path path = new Path();
        path.setName(pathPost.getName());

        return pathRepository.save(path);
    }

    @Override
    public List<Path> getPaths() {
        boolean isAdmin = SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new
                SimpleGrantedAuthority
                ("ADMIN"));
        if (isAdmin) {
            return pathRepository.findAll();
        } else {
            Set<Long> paths = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                    .getPaths();
            return pathRepository.findAll(paths);
        }
    }

    @Override
    @Transactional
    public boolean addUserToPath(long id, Set<Long> studentIds) {
        Path path = pathRepository.findOne(id);
        if (path == null) {
            return false;
        }
        List<User> users = userRepository.findAll(studentIds);

        for (User user : users) {
            user.getPaths().add(path);
        }
        userRepository.save(users);
        return true;
    }


}
