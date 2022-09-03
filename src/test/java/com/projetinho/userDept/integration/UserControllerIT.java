package com.projetinho.userDept.integration;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.repository.UserRepository;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;
import com.projetinho.userDept.util.UserCreator;
import com.projetinho.userDept.util.UserPostRequestBodyCreator;
import com.projetinho.userDept.util.UserPutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayName("Test for User Integration")
class UserControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private UserRepository userRepository;
    private User expectedUser;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        expectedUser = UserCreator.createdUserValid();
    }

    @Test
    @DisplayName("findAll return user list when successful")
    void findAll_ReturnUserList_WhenSuccessful() {
        User userSaved = userRepository.save(expectedUser);
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<>() {
        };

        System.out.println();
        System.out.println("UserSaved: " + userSaved);

        ResponseEntity<List<User>> userListFound = testRestTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                typeReference
        );

        assertAll(
                () -> assertNotNull(userListFound),
                () -> assertNotNull(userListFound.getBody()),
                () -> assertEquals(1, userListFound.getBody().size()),
                () -> assertTrue(userListFound.getBody().contains(userSaved)),
                () -> assertEquals(HttpStatus.OK, userListFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findAll return empty list when dont exist user in given database")
    void findAll_ReturnEmptyList_WhenDontExistsUsersInDataBase() {
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<User>> expectedEmptyList = testRestTemplate.exchange(
                "/users",
                HttpMethod.GET,
                null,
                typeReference
        );

        assertAll(
                () -> assertNotNull(expectedEmptyList),
                () -> assertTrue(expectedEmptyList.getBody().isEmpty()),
                () -> assertEquals(HttpStatus.OK, expectedEmptyList.getStatusCode())
        );
    }

    @Test
    @DisplayName("FindById return user when successful")
    void findById_ReturnUser_WhenSuccessful() {
        User userSaved = userRepository.save(expectedUser);

        ResponseEntity<User> userFound = testRestTemplate.exchange(
                "/users/{id}",
                HttpMethod.GET,
                null,
                User.class,
                userSaved.getIdUser()
        );

        assertAll(
                () -> assertNotNull(userFound),
                () -> assertNotNull(userFound.getBody()),
                () -> assertEquals(userSaved.getIdUser(), userFound.getBody().getIdUser()),
                () -> assertEquals(HttpStatus.OK, userFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findById return statusCode BadRequest when id not found")
    void findById_ReturnStatusCodeBadRequest_WhenIdNotFound() {
        ResponseEntity<User> expectedBadRequest = testRestTemplate.exchange(
                "/users/{id}",
                HttpMethod.GET,
                null,
                User.class,
                1
        );

        assertAll(
                () -> assertNotNull(expectedBadRequest),
                () -> assertNull(expectedBadRequest.getBody().getIdUser()),
                () -> assertEquals(HttpStatus.BAD_REQUEST, expectedBadRequest.getStatusCode())
        );
    }

    @Test
    @DisplayName("findByName return user when successful")
    void findByName_ReturnUser_WhenSuccessful() {
        User userSaved = userRepository.save(expectedUser);
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<User>> userListFound = testRestTemplate.exchange(
                "/users/find?name=" + userSaved.getName(),
                HttpMethod.GET,
                null,
                typeReference
        );

        assertAll(
                () -> assertNotNull(userListFound.getBody()),
                () -> assertFalse(userListFound.getBody().isEmpty()),
                () -> assertEquals(1, userListFound.getBody().size()),
                () -> assertEquals(HttpStatus.OK, userListFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findByName return empty list when user name not found in given database")
    void findByName_ReturnEmptyList_WhenUserNameNotFound() {
        ParameterizedTypeReference<List<User>> typeReference = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<User>> userEmptyListExpected = testRestTemplate.exchange(
                "/users/find?name=vinicius",
                HttpMethod.GET,
                null,
                typeReference
        );

        assertAll(
                () -> assertTrue(userEmptyListExpected.getBody().isEmpty()),
                () -> assertEquals(HttpStatus.OK, userEmptyListExpected.getStatusCode())
        );
    }

    @Test
    @DisplayName("save Insert user when successful")
    void save_InsertUser_WhenSuccessful() {
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBody();

        ResponseEntity<User> userSaved = testRestTemplate.exchange(
                "/users",
                HttpMethod.POST,
                new HttpEntity<>(userPostRequestBody),
                User.class
        );

        assertAll(
                () -> assertNotNull(userSaved),
                () -> assertEquals(HttpStatus.CREATED, userSaved.getStatusCode())
        );
    }

    @Test
    @DisplayName("save return statusCode BadRequest when user not saved")
    void save_ReturnStatusCodeBadRequest_WhenUserNotSaved(){
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBodyInvalid();

        ResponseEntity<User> expectedBadRequest = testRestTemplate.exchange(
                "/users",
                HttpMethod.POST,
                new HttpEntity<>(userPostRequestBody),
                User.class
        );

        assertAll(
                () -> assertNotNull(expectedBadRequest),
                () -> assertEquals(HttpStatus.BAD_REQUEST, expectedBadRequest.getStatusCode())
        );
    }

    @Test
    @DisplayName("replace updated user when successful")
    void replace_UpdatedUser_WhenSuccessful(){
        User userSaved = userRepository.save(expectedUser);
        UserPutRequestBody userPutRequestBody = UserPutRequestBodyCreator.createUserPutRequestBody();
        userPutRequestBody.setIdUser(userSaved.getIdUser());

        ResponseEntity<Void> userReplaced = testRestTemplate.exchange(
                "/users",
                HttpMethod.PUT,
                new HttpEntity<>(userPutRequestBody),
                Void.class
        );

        assertAll(
                () -> assertNotNull(userReplaced),
                () -> assertEquals(HttpStatus.NO_CONTENT, userReplaced.getStatusCode())
        );
    }

    @Test
    @DisplayName("replace return BadRequest statusCode when user not found")
    void replace_ReturnBadRequestStatusCode_WhenUserNotFound(){
        User userSaved = userRepository.save(expectedUser);
        UserPutRequestBody userPutRequestBodyInvalid = UserPutRequestBodyCreator.createUserPutRequestBodyInvalid();

        ResponseEntity<Void> expectedBadRequest = testRestTemplate.exchange(
                "/users",
                HttpMethod.PUT,
                new HttpEntity<>(userPutRequestBodyInvalid),
                Void.class
        );

        assertAll(
                () -> assertNotNull(expectedBadRequest),
                () -> assertEquals(HttpStatus.BAD_REQUEST, expectedBadRequest.getStatusCode())
        );
    }

    @Test
    @DisplayName("delete remove user when successful")
    void delete_RemoveUser_WhenSuccessful(){
        User userSaved = userRepository.save(expectedUser);

        ResponseEntity<Void> userDeleted = testRestTemplate.exchange(
                "/users/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                userSaved.getIdUser()
        );

        assertAll(
                () -> assertNotNull(userDeleted),
                () -> assertEquals(HttpStatus.NO_CONTENT, userDeleted.getStatusCode())
        );
    }

    @Test
    @DisplayName("delete return BadRequest statusCode when user not found")
    void delete_ReturnBadRequestStatusCode_WhenUserNotFound(){
        ResponseEntity<Void> expectedBadRequest = testRestTemplate.exchange(
                "/users/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                99
        );

        assertAll(
                () -> assertNotNull(expectedBadRequest),
                () -> assertEquals(HttpStatus.BAD_REQUEST, expectedBadRequest.getStatusCode())
        );
    }

}
