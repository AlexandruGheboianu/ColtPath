package com.ghb.coltpath.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ghb.coltpath.Application;
import com.ghb.coltpath.dto.reader.PathGet;
import com.ghb.coltpath.dto.writer.PathPost;
import com.ghb.coltpath.dto.writer.RequestOutcomeMessage;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by Ghebo on 1/20/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class PathResourceTest extends BaseIntegrationTest {


    @Before
    public void init() {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        userRepository.deleteAll();
        pathRepository.deleteAll();
    }

    @Test
    public void showPathTest() {
        User user = createUser();
        Path path = new Path();
        path.setName("Beginner");
        path = pathRepository.save(path);
        ResponseEntity<PathGet> entity = getEntity("/paths/" + path.getId(), PathGet.class, "awesome", "test");
        assertEquals(HttpStatus.FORBIDDEN, entity.getStatusCode());

        user = addRole(user, "ADMIN");
        entity = getEntity("/paths/" + path.getId(), PathGet.class, "awesome", "test");
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        PathGet resp = entity.getBody();
        assertEquals("Beginner", resp.getName());
        assertNotNull(resp.getCreationDate());
        assertNotNull(resp.getCreatedBy());
        assertNotNull(resp.getPathId());
        assertTrue(resp.getPathId() > 0);

        user = createUser("test", "jdoe@example.com", "test");
        user = addRole(user, "STUDENT");
        user.getPaths().add(path);
        userRepository.save(user);
        entity = getEntity("/paths/" + path.getId(), PathGet.class, "test", "test");
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    public void showPaths() {
        User student = createUser();
        student = addRole(student, "STUDENT");
        Path path1 = new Path();
        path1.setName("a");
        Path path2 = new Path();
        path2.setName("b");
        Path path3 = new Path();
        path3.setName("c");
        List<Path> saved = pathRepository.save(Arrays.asList(path1, path2, path3));
        saved.remove(2);
        student.getPaths().addAll(saved);
        student = userRepository.save(student);

        ResponseEntity<List> paths = getEntity("/paths", List.class, "awesome", "test");
        assertEquals(2, paths.getBody().size());

        addRole(student, "ADMIN");
        paths = getEntity("/paths", List.class, "awesome", "test");
        assertEquals(3, paths.getBody().size());

    }

    @Test
    public void createPathTest() throws JsonProcessingException {
        User user = createUser();
        PathPost pathPost = new PathPost();
        pathPost.setName("Advanced");

        ResponseEntity<PathGet> responseEntity = postEntity("/paths", pathPost, "awesome", "test", PathGet.class);
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());

        user = addRole(user, "ADMIN");
        pathPost.setName("");
        responseEntity = postEntity("/paths", pathPost, "awesome", "test", PathGet.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        pathPost.setName("Advanced");
        responseEntity = postEntity("/paths", pathPost, "awesome", "test", PathGet.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responseEntity.getBody().getName(), "Advanced");


    }

    @Test
    public void addUserToPathTest() throws JsonProcessingException, InterruptedException {
        User user = createUser();

        User student = createUser("student", "student@example.com", "pwd");
        student = addRole(student, "STUDENT");
        Path path = new Path();
        path.setName("Beginner");
        path = pathRepository.save(path);
        Set<Long> ids = new HashSet<>();
        Map<String, Object> payload = new HashMap<>();



        ResponseEntity<RequestOutcomeMessage> message = postEntity("/paths/" + path.getId() + "/users"
                , payload, "awesome", "test", RequestOutcomeMessage.class);
        assertEquals(HttpStatus.BAD_REQUEST, message.getStatusCode());
        ids.add(student.getId());
        payload.put("userIds", ids);
        message = postEntity("/paths/" + path.getId() + "/users"
                , payload, "awesome", "test", RequestOutcomeMessage.class);
        assertEquals(HttpStatus.FORBIDDEN, message.getStatusCode());
        user = addRole(user, "ADMIN");

        message = postEntity("/paths/500000/users"
                , payload, "awesome", "test", RequestOutcomeMessage.class);
        assertEquals(HttpStatus.BAD_REQUEST, message.getStatusCode());

        message = postEntity("/paths/" + path.getId() + "/users/"
                , payload, "awesome", "test", RequestOutcomeMessage.class);
        assertEquals(HttpStatus.OK, message.getStatusCode());
        assertEquals("User added successfully to path", message.getBody().getMessage());
        student = userRepository.findOne(student.getId());
        assertFalse(student.getPaths().isEmpty());
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        pathRepository.deleteAll();
    }
}
