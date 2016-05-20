package com.ghb.coltpath.core.repository;

import com.ghb.coltpath.core.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Created by agheboianu on 20.05.2016.
 */

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends CrudRepository<T, Long> {
    T findOneByLogin(String login);

    T findOneByEmail(String email);
}
