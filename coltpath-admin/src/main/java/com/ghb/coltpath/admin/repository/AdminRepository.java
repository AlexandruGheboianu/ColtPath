package com.ghb.coltpath.admin.repository;

import com.ghb.coltpath.core.model.Admin;
import com.ghb.coltpath.core.repository.UserBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by agheboianu on 20.05.2016.
 */
public interface AdminRepository extends UserBaseRepository<Admin> {
}
