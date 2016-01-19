package com.ghb.coltpath.repository;

import com.ghb.coltpath.model.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Ghebo on 1/19/2016.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findOneByName(String name);
}
