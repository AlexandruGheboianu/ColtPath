package com.ghb.coltpath.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ghb.coltpath.Application;
import com.ghb.coltpath.dto.reader.UserGet;
import com.ghb.coltpath.dto.writer.RequestOutcomeMessage;
import com.ghb.coltpath.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Ghebo on 1/15/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class UserResourceTest extends BaseIntegrationTest {


    @Before
    public void init() {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    @Test
    public void userCanGetSelf() {
        User user = createUser();
        Date previousLoginDate = user.getLastLogin();
        ResponseEntity<UserGet> entity = getEntity("/users/awesome", UserGet.class, "awesome", "test");
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        user = userRepository.findOne(user.getId());
        assertNotEquals(previousLoginDate, user.getLastLogin());

        UserGet retrieved = entity.getBody();

        assertNotNull(retrieved);
        assertEquals(retrieved.getLogin(), "awesome");
        assertEquals(retrieved.getEmail(), "test@example.com");
        assertEquals(retrieved.getFirstName(), "John");
        assertEquals(retrieved.getLastName(), "Doe");
        assertTrue(retrieved.isActive());
        assertNotEquals(previousLoginDate, retrieved.getLastLogin());

        Map<String, Object> resp = getAsMapObject("/users/awesome", "awesome", "test");
        assertTrue(resp.containsKey("_links"));

        ResponseEntity<RequestOutcomeMessage> entity2 = getEntity("/users/otheruser", RequestOutcomeMessage.class, "awesome", "test");

        assertEquals(HttpStatus.FORBIDDEN, entity2.getStatusCode());
        assertEquals("Access is denied", entity2.getBody().getMessage());
    }

    @Test
    public void userCanChangePassword() throws JsonProcessingException {
        User user = createUser();
        //Building the Request body data
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("oldPassword", "test");
        requestBody.put("newPassword", "12345678");
        requestBody.put("passwordConfirm", "12345678");

        Map<String, Object> apiResponse = postMapObject("/users/awesome/pwd_change", requestBody, "awesome", "test");
        assertNotNull(apiResponse);

        //Asserting the response of the API.

        String message = apiResponse.get("message").toString();
        assertEquals("Password changed successfully", message);

        User userNewPwd = userRepository.findOneByEmail("test@example.com");
        assertTrue(new BCryptPasswordEncoder().matches("12345678", userNewPwd.getPassword()));
        assertNotEquals(user.getLastModification().getTime(), userNewPwd.getLastModification().getTime());
        userRepository.deleteAll();
    }

    @Test
    public void registerUserWithValidationPassing() throws JsonProcessingException {
        User currentUser = createUser();
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("email", "test1@example.com");
        requestBody.put("firstName", "John");
        requestBody.put("login", "awesomes");
        requestBody.put("lastName", "Doe");
        requestBody.put("password", "test1234");
        requestBody.put("passwordConfirm", "test1234");

        //Invoking the API
        Map<String, Object> apiResponse = postMapObject("/users", requestBody, "awesome", "test");
        assertNotNull(apiResponse);

        //Asserting the response of the API.

        String message = apiResponse.get("message").toString();

        assertEquals("Access is denied", message);

        addRole(currentUser, "ADMIN");
        apiResponse = postMapObject("/users", requestBody, "awesome", "test");

        message = apiResponse.get("message").toString();
        assertEquals("User created successfuly", message);

        User user = userRepository.findOneByEmail("test1@example.com");

        assertNotNull(user.getId());
        assertEquals("test1@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("awesomes", user.getLogin());
        assertTrue(new BCryptPasswordEncoder().matches("test1234", user.getPassword()));
        assertFalse(user.isActive());
        assertTrue(user.getRoles().iterator().next().getName().equals("STUDENT"));
        assertNotNull(user.getCreationDate());
        assertNotNull(user.getLastModification());
        assertEquals(user.getCreationDate(), user.getLastModification());
        assertNull(user.getLastLogin());
        assertNotNull(user.getConfirmationUrl());

        userRepository.deleteAll();
    }

    @Test
    public void registerUserWithValidationErrors() throws IOException {
        User currentUser = createUser();
        currentUser = addRole(currentUser, "ADMIN");
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("email", "");
        requestBody.put("login", "awesome");
        requestBody.put("firstName", "");
        requestBody.put("lastName", "");
        requestBody.put("password", "test");

        ResponseEntity<RequestOutcomeMessage> apiResponse = postEntity("/users", requestBody, "awesome", "test",
                RequestOutcomeMessage.class);

        assertNotNull(apiResponse);
        assertEquals(HttpStatus.BAD_REQUEST, apiResponse.getStatusCode());
        String message = apiResponse.getBody().getMessage(); //apiResponse.get("message").toString();
        assertEquals("ERROR", message);
        assertNotNull(apiResponse.getBody().getFields());
        assertFalse(apiResponse.getBody().getFields().isEmpty());

        requestBody = new HashMap<String, Object>();
        requestBody.put("email", "test1@example.com");
        requestBody.put("login", "awesomess");
        requestBody.put("firstName", "John");
        requestBody.put("lastName", "Doe");
        requestBody.put("password", "test1234");

        apiResponse = postEntity("/users", requestBody, "awesome", "test", RequestOutcomeMessage.class);

        assertNotNull(apiResponse);
        message = apiResponse.getBody().getMessage();

        assertEquals("User created successfuly", message);

        apiResponse = postEntity("/users", requestBody, "awesome", "test", RequestOutcomeMessage.class);

        assertNotNull(apiResponse);
        message = apiResponse.getBody().getMessage(); //apiResponse.get("message").toString();
        assertEquals("ERROR", message);
        assertEquals(2, apiResponse.getBody().getFields().size());

    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
    }
}