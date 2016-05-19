package com.ghb.coltpath.elearning.service;

import com.ghb.coltpath.core.model.User;
import com.ghb.coltpath.elearning.dto.writer.PasswordChangePost;
import com.ghb.coltpath.elearning.dto.writer.UserPost;
import com.ghb.coltpath.elearning.model.Student;
import com.ghb.coltpath.elearning.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Ghebo on 1/16/2016.
 */
@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public void registerUser(UserPost userPost) {
        Student student = new Student();
        student.setLogin(userPost.getLogin());
        student.setPassword(new BCryptPasswordEncoder().encode(userPost.getPassword()));
        student.setEmail(userPost.getEmail());
        student.setFirstName(userPost.getFirstName());
        student.setLastName(userPost.getLastName());
        student.setActive(true);
        student.setState("CREATED");
        studentRepository.saveAndFlush(student);
    }

    public void changePassword(String login, PasswordChangePost passwordChangePost) {
        Student student = studentRepository.findOneByLogin(login);
        student.setPassword(new BCryptPasswordEncoder().encode(passwordChangePost.getNewPassword()));
        studentRepository.save(student);
    }


    public Student getUser(String login) {
        return studentRepository.findOneByLogin(login);
    }
}
