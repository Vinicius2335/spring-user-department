package com.projetinho.userDept.repository;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.util.UserCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Test for user repository")
class UserRepositoryTest {
    @Autowired private UserRepository userRepository;
    private User expectedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        expectedUser = UserCreator.createdUserValid();
    }

    @Test
    @DisplayName("save insert user when successful")
    void save_InsertUser_WhenSuccessful(){
        User actualUser = userRepository.save(expectedUser);

        assertAll(
                () -> assertNotNull(actualUser),
                () -> assertNotNull(actualUser.getIdUser()),
                () -> assertEquals(expectedUser.getName(), actualUser.getName())
        );
    }
    @Test
    @DisplayName("save Throw ConstraintViolationException when user name is null or empty")
    void save_ThrowConstraintViolationException_WhenUserNameIsNullOrEmpty(){
        User user = new User();
        assertThrows(ConstraintViolationException.class, () -> userRepository.save(user));
    }

    @Test
    @DisplayName("findAll return list user when successful")
    void findAll_ReturnListUser_WhenSuccessful(){
        User usertSave = userRepository.save(expectedUser);
        List<User> userListFound = userRepository.findAll();

        assertAll(
                () -> assertNotNull(userListFound),
                () -> assertEquals(1, userListFound.size()),
                () -> assertTrue(userListFound.contains(usertSave))
        );
    }
    @Test
    @DisplayName("findAll return Empty list user when user not found")
    void findAll_ReturnEmptyList_WhenUserNotFound(){
        List<User> userListFound = userRepository.findAll();

        assertTrue(userListFound.isEmpty());
    }

    @Test
    @DisplayName("findById return user found by id when successful")
    void findById_ReturnUser_WhenSuccessful(){
        User userSave = userRepository.save(expectedUser);
        User userFound = userRepository.findById(userSave.getIdUser()).get();

        assertAll(
                () -> assertNotNull(userFound),
                () -> assertNotNull(userFound.getIdUser()),
                () -> assertEquals(expectedUser.getName(), userSave.getName())
        );
    }

    @Test
    @DisplayName("findById return emptyList when id not found")
    void findById_ReturnEmptyList_WhenIdNotFound(){
        User user = UserCreator.createUserNotFound();
        Optional<User> userFound = userRepository.findById(user.getIdUser());

        assertTrue(userFound.isEmpty());
    }

    @Test
    @DisplayName("findByName return user found by name when successful")
    void findByName_ReturnUser_WhenSuccessful(){
        User userSave = userRepository.save(expectedUser);
        List<User> userListFound = userRepository.findByName(userSave.getName());

        assertAll(
                () -> assertNotNull(userListFound),
                () -> assertEquals(1, userListFound.size()),
                () -> assertTrue(userListFound.contains(userSave))
        );
    }

    @Test
    @DisplayName("findByName return empty List when name not found")
    void findByName_ReturnEmptyList_WhenNameNotFound(){
        User user = UserCreator.createUserNotFound();
        List<User> userFound = userRepository.findByName(user.getName());

        assertTrue(userFound.isEmpty());
    }

    @Test
    @DisplayName("save update user when successful")
    void replace_UpdateUser_WhenSuccessful(){
        User userSave = userRepository.save(expectedUser);
        userSave.setName("Ginkko");
        User userReplaced = userRepository.save(userSave);

        assertAll(
                () -> assertNotNull(userReplaced),
                () -> assertNotNull(userReplaced.getIdUser()),
                () -> assertEquals(userSave.getIdUser(), userReplaced.getIdUser())
        );
    }

    @Test
    @DisplayName("delete remove user when successful")
    void delete_RemoveUser_WhenSuccessful(){
        User userSaved = userRepository.save(expectedUser);
        userRepository.delete(userSaved);
        Optional<User> userDelete = userRepository.findById(userSaved.getIdUser());

        assertTrue(userDelete.isEmpty());
    }
}