package com.ghb.coltpath.admin.repository;

import com.ghb.coltpath.core.model.Student;
import com.ghb.coltpath.core.repository.UserBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Ghebo on 1/6/2016.
 */
public interface StudentRepository extends UserBaseRepository<Student> {

}
