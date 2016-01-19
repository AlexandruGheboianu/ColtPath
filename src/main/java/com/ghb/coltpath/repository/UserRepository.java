package com.ghb.coltpath.repository;

import com.ghb.coltpath.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ghebo on 1/6/2016.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findOneByEmail(String email);

    User findOneByLogin(String login);
}
