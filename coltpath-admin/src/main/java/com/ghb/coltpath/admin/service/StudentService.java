package com.ghb.coltpath.admin.service;

import com.ghb.coltpath.admin.dto.StudentPost;
import com.ghb.coltpath.admin.repository.StudentRepository;
import com.ghb.coltpath.core.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by agheboianu on 20.05.2016.
 */
@Service
public class StudentService {
    private static final String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    @Autowired
    StudentRepository studentRepository;


    public void registerStudent(StudentPost studentPost) {
        Student student = new Student();
        student.setLogin(studentPost.getLogin());
        student.setPassword(new BCryptPasswordEncoder().encode(studentPost.getPassword() == null ? newRandomPassword() : studentPost.getPassword()));
        student.setEmail(studentPost.getEmail());
        student.setFirstName(studentPost.getFirstName());
        student.setLastName(studentPost.getLastName());
        student.setActive(true);
        student.setState("CREATED");
        studentRepository.save(student);
    }

    private String newRandomPassword() {
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            double index = Math.random() * characters.length();
            buffer.append(characters.charAt((int) index));
        }
        return buffer.toString();
    }
}
