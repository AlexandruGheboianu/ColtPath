package com.ghb.coltpath.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.ghb.coltpath.Application;
import com.ghb.coltpath.dto.writer.ProjectPost;
import com.ghb.coltpath.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by agheboianu on 25.01.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class ProjectResourceTest extends BaseIntegrationTest {

    @Before
    public void init() {
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        userRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void createProjectTest() throws JsonProcessingException {
        User user = createUser();
        user = addRole(user, "ADMIN");

        Map<String, Object> response = postMapObject("/projects", new ProjectPost("Java"), "awesome", "test");
        assertEquals("Java", response.get("name"));

        response = getAsMapObject("/projects?page=0&size=30&sort=name,asc", "awesome", "test");
        assertTrue(response.containsKey("page"));

    }


}
