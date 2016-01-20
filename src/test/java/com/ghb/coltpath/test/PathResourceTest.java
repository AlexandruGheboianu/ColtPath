package com.ghb.coltpath.test;

import com.ghb.coltpath.Application;
import com.ghb.coltpath.dto.reader.PathGet;
import com.ghb.coltpath.model.Path;
import com.ghb.coltpath.model.User;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ghebo on 1/20/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class PathResourceTest extends BaseIntegrationTest {

    @Test
    public void showPathTest() {
        User user = createUser();
        Path path = new Path();
        path.setName("Beginner");
        pathRepository.save(path);
        ResponseEntity<PathGet> entity = getEntity("/paths/1", PathGet.class, "awesome", "test");
        assertEquals(HttpStatus.FORBIDDEN, entity.getStatusCode());
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        pathRepository.deleteAll();
    }
}
