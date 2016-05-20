package com.ghb.coltpath.elearning.service;

import com.ghb.coltpath.elearning.dto.writer.PasswordChangePost;
import com.ghb.coltpath.core.model.Student;
import com.ghb.coltpath.elearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by Ghebo on 1/16/2016.
 */
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public void changePassword(String login, PasswordChangePost passwordChangePost) {
        Student student = studentRepository.findOneByLogin(login);
        student.setPassword(new BCryptPasswordEncoder().encode(passwordChangePost.getNewPassword()));
        studentRepository.save(student);
    }

}
