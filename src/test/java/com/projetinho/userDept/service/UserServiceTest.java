package com.projetinho.userDept.service;

import com.projetinho.userDept.exception.BadRequestException;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for user service")
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepositoryMock;
    private User expectedUser;
    private List<User> expectedUserList;

    @BeforeEach
    void setUp() {
        userRepositoryMock.deleteAll();
        expectedUser = UserCreator.createdUserValid();
        expectedUserList = List.of(expectedUser);

        // save
        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(expectedUser);

        // listAll
        BDDMockito.when(userRepositoryMock.findAll())
                .thenReturn(expectedUserList);

        // findById
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(expectedUser));

        // findByName
        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(expectedUserList);

        // delete
        BDDMockito.doNothing().when(userRepositoryMock).delete(ArgumentMatchers.any(User.class));
    }

    @Test
    @DisplayName("save insert user when successful")
    void save_InsertUser_WhenSuccessful() {
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBody();
        User userSaved = userService.save(userPostRequestBody);

        assertAll(
                () -> assertNotNull(userSaved),
                () -> assertNotNull(userSaved.getIdUser()),
                () -> assertEquals(expectedUser.getName(), userSaved.getName())
        );
    }

    @Test
    @DisplayName("save throw ConstraintValidationException when user invalid")
    void save_ThrowConstraintValidationException_WhenUserInvalid() {
        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenThrow(ConstraintViolationException.class);
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createdUserPostRequestBodyInvalid();

        assertThrows(ConstraintViolationException.class, () -> userService.save(userPostRequestBody));
    }

    @Test
    @DisplayName("listAll return user list when successful")
    void listAll_ReturnUserList_WhenSuccessful() {
        List<User> userListFound = userService.findAll();

        assertAll(
                () -> assertNotNull(userListFound),
                () -> assertEquals(expectedUserList, userListFound),
                () -> assertEquals(1, userListFound.size()),
                () -> assertTrue(userListFound.contains(expectedUser))
        );
    }

    @Test
    @DisplayName("findById return user when successful")
    void findById_ReturnUser_WhenSuccessful(){
        User userFound = userService.findById(expectedUser.getIdUser());

        assertAll(
                () -> assertNotNull(userFound),
                () -> assertNotNull(userFound.getIdUser()),
                () -> assertEquals(expectedUser, userFound)
        );
    }

    @Test
    @DisplayName("findById throws BadRequestException when id not found")
    void findById_ThrowBadRequestException_WhenIdNotFound(){
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> userService.findById(99L));
    }

    @Test
    @DisplayName("findByName return User list when successful")
    void findByName_ReturnUserList_WhenSuccessful(){
        List<User> userFound = userService.findByName(expectedUser.getName());

        assertAll(
                () -> assertNotNull(userFound),
                () -> assertEquals(expectedUserList, userFound),
                () -> assertTrue(userFound.contains(expectedUser))
        );
    }

    @Test
    @DisplayName("findByName return empty list when user name not found")
    void findByName_ReturnEmptyList_WhenNameNotFound(){
        BDDMockito.when(userRepositoryMock.findByName(ArgumentMatchers.anyString()))
                        .thenReturn(List.of());
        List<User> userListFound = userService.findByName(expectedUser.getName());

        assertTrue(userListFound.isEmpty());
    }

    @Test
    @DisplayName("replace update user when successful")
    void replace_UpdateUser_WhenSuccessful(){
        UserPutRequestBody userPutRequestBody = UserPutRequestBodyCreator.createUserPutRequestBody();

        assertDoesNotThrow(() -> userService.replace(userPutRequestBody));
    }

    @Test
    @DisplayName("replace throws ConstraintValidationException when user invalid")
    void replace_ThrowsConstraintValidationException_WhenUserInvalid(){
        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenThrow(ConstraintViolationException.class);
        UserPutRequestBody userPutRequestBody = UserPutRequestBodyCreator.createUserPutRequestBodyInvalid();

        assertThrows(ConstraintViolationException.class, ()-> userService.replace(userPutRequestBody));
    }

    @Test
    @DisplayName("delete remove user when successful")
    void delete_RemoveUser_WhenSuccessful(){
        assertDoesNotThrow(() -> userService.delete(1L));
    }
}