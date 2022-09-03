package com.projetinho.userDept.controller;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.model.User;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;
import com.projetinho.userDept.service.UserService;
import com.projetinho.userDept.util.UserCreator;
import com.projetinho.userDept.util.UserPostRequestBodyCreator;
import com.projetinho.userDept.util.UserPutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for user controller")
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userServiceMock;
    private User expectedUser;
    private List<User> expectedUserList;

    @BeforeEach
    void setUp() {
        expectedUser = UserCreator.createdUserValid();
        expectedUserList = List.of(expectedUser);

        // save
        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenReturn(expectedUser);

        // listAll
        BDDMockito.when(userServiceMock.findAll())
                .thenReturn(expectedUserList);

        // findById
        BDDMockito.when(userServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(expectedUser);

        // findByName
        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(expectedUserList);

        // replace
        BDDMockito.doNothing().when(userServiceMock).replace(ArgumentMatchers.any(UserPutRequestBody.class));

        // delete
        BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("save insert user when successful")
    void save_InsertUser_WhenSuccessful() {
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBody();
        ResponseEntity<User> userSaved = userController.save(userPostRequestBody);

        assertAll(
                () -> assertNotNull(userSaved),
                () -> assertEquals(HttpStatus.CREATED, userSaved.getStatusCode())
        );
    }

    @Test
    @DisplayName("save throws ConstraintViolationnException when user invalid")
    void save_ThrowConstraintValidationException_WhenUserInvalid() {
        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenThrow(ConstraintViolationException.class);
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBodyInvalid();

        assertThrows(ConstraintViolationException.class, () -> userController.save(userPostRequestBody));
    }

    @Test
    @DisplayName("listAll return user list when successful")
    void listAll_ReturnUserList_WhenSuccessful() {
        ResponseEntity<List<User>> userListFound = userController.findAll();

        assertAll(
                () -> assertNotNull(userListFound.getBody()),
                () -> assertEquals(1, userListFound.getBody().size()),
                () -> assertEquals(HttpStatus.OK, userListFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findById return user list when successful")
    void findById_ReturnUserList_WhenSuccessful() {
        ResponseEntity<User> userFound = userController.findById(1L);

        assertAll(
                () -> assertNotNull(userFound),
                () -> assertNotNull(userFound.getBody()),
                () -> assertNotNull(userFound.getBody().getIdUser()),
                () -> assertEquals(expectedUser, userFound.getBody()),
                () -> assertEquals(HttpStatus.OK, userFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findById throw BadRequestException when id user not found")
    void findById_ThrowBadRequestException_WhenIdUserNotFound(){
        BDDMockito.when(userServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> userController.findById(99L));
    }

    @Test
    @DisplayName("findByName return user list when successful")
    void findByName_ReturnUserList_WhenSuccessful(){
        ResponseEntity<List<User>> userListFound = userController.findByName(expectedUser.getName());

        assertAll(
                () -> assertNotNull(userListFound),
                () -> assertNotNull(userListFound.getBody()),
                () -> assertEquals(1, userListFound.getBody().size()),
                () -> assertTrue(userListFound.getBody().contains(expectedUser)),
                () -> assertEquals(HttpStatus.OK, userListFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findByName return empty list when user name not found")
    void findByName_ReturnEmptyList_WhenUserNameNotFound(){
        BDDMockito.when(userServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of());
        User user = UserCreator.createUserNotFound();
        ResponseEntity<List<User>> userFound = userController.findByName(user.getName());

        assertTrue(userFound.getBody().isEmpty());
        assertEquals(HttpStatus.OK, userFound.getStatusCode());
    }

    @Test
    @DisplayName("replace update user when successful")
    void replace_UpdateUser_WhenSuccessful(){
        UserPutRequestBody userPutRequestBody = UserPutRequestBodyCreator.createUserPutRequestBody();
        ResponseEntity<Void> userReplaced = userController.replace(userPutRequestBody);

        assertAll(
                () -> assertNotNull(userReplaced),
                () -> assertEquals(HttpStatus.NO_CONTENT, userReplaced.getStatusCode())
        );
    }

    @Test
    @DisplayName("delete remove user when successful")
    void delete_RemoveUser_WhenSuccessful(){
        ResponseEntity<Void> userDeleted = userController.delete(1L);

        assertAll(
                () -> assertNotNull(userDeleted),
                () -> assertEquals(HttpStatus.NO_CONTENT, userDeleted.getStatusCode())
        );
    }
}