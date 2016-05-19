package com.ghb.coltpath.elearning.repository;

import com.ghb.coltpath.core.model.User;
import com.ghb.coltpath.elearning.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ghebo on 1/6/2016.
 */
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findOneByEmail(String email);

    Student findOneByLogin(String login);
}
