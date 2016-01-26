package com.ghb.coltpath.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghb.coltpath.model.Role;
import com.ghb.coltpath.model.User;
import com.ghb.coltpath.repository.PathRepository;
import com.ghb.coltpath.repository.ProjectRepository;
import com.ghb.coltpath.repository.RoleRepository;
import com.ghb.coltpath.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Created by Ghebo on 1/16/2016.
 */
public abstract class BaseIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PathRepository pathRepository;

    @Autowired
    ProjectRepository projectRepository;

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected User createUser() {
        User user = new User();
        user.setLogin("awesome");
        user.setEmail("test@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(new BCryptPasswordEncoder().encode("test"));
        user.setActive(true);

        return userRepository.save(user);
    }

    public User addRole(User user, String roleName) {
        Role role = roleRepository.findOneByName(roleName);

        if (role == null) {
            role = new Role();
            role.setName(roleName);
            role = roleRepository.save(role);
        }
        user.getRoles().add(role);
        return userRepository.save(user);
    }


    protected User createUser(String login, String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setLogin(login);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setActive(true);
        return userRepository.save(user);
    }

    protected User createUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    protected <T> ResponseEntity<T> getEntity(String path, Class<T> responseClass, String user, String pass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));


        ResponseEntity<T> entity = new TestRestTemplate(user, pass).getForEntity
                ("http://localhost:8888" + path, responseClass);
        return entity;
    }

    protected Map<String, Object> getAsMapObject(String path, String user, String pass) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Map<String, Object> entity = new TestRestTemplate(user, pass).getForObject
                ("http://localhost:8888" + path, Map.class);
        return entity;
    }

    protected Map<String, Object> postMapObject(String path, Object payload, String user, String pass) throws JsonProcessingException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(payload), requestHeaders);

        //Invoking the API

        Map<String, Object> apiResponse =
                new TestRestTemplate(user, pass).postForObject("http://localhost:8888" + path, httpEntity, Map
                        .class, Collections.EMPTY_MAP);
        return apiResponse;

    }


    protected <T> ResponseEntity<T> postEntity(String path, Object payload, String user, String pass,
                                               Class<T> responseType) throws
            JsonProcessingException {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        //Creating http entity object with request body and headers
        HttpEntity<String> httpEntity =
                new HttpEntity<String>(OBJECT_MAPPER.writeValueAsString(payload), requestHeaders);

        return new TestRestTemplate(user, pass).postForEntity("http://localhost:8888" + path, httpEntity, responseType,
                Collections.EMPTY_MAP);
    }
}
