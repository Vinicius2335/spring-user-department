package com.projetinho.userDept.integration;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.repository.DepartamentRepository;
import com.projetinho.userDept.repository.UserRepository;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import com.projetinho.userDept.util.DepartamentCreator;
import com.projetinho.userDept.util.DepartamentPostRequestBodyCreator;
import com.projetinho.userDept.util.DepartamentPutRequestBodyCreator;
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
@DisplayName("Test for departament integration")
class DepartamentControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private DepartamentRepository departamentRepository;
    @Autowired
    private UserRepository userRepository;
    private Departament expectedDepartament;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        departamentRepository.deleteAll();
        expectedDepartament = DepartamentCreator.createdDepartamentValid();
    }

    @Test
    @DisplayName("listAll return departament list when successful")
    void listAll_ReturnListDepartament_WhenSuccessful() {
        Departament actualDepartament = departamentRepository.save(expectedDepartament);
        ParameterizedTypeReference<List<Departament>> typeReference = new ParameterizedTypeReference<>() {
        };

        ResponseEntity<List<Departament>> actualDepartamentList = testRestTemplate.exchange(
                "/departament",
                HttpMethod.GET,
                null,
                typeReference
        );

        assertAll(
                () -> assertNotNull(actualDepartamentList.getBody()),
                () -> assertEquals(1, actualDepartamentList.getBody().size()),
                () -> assertTrue(actualDepartamentList.getBody().contains(actualDepartament)),
                () -> assertEquals(HttpStatus.OK, actualDepartamentList.getStatusCode())
        );
    }

    @Test
    @DisplayName("findById return departament founded by id when successfull ")
    void findById_ReturnDepartament_WhenSuccessful(){
        Departament departamentSave = departamentRepository.save(expectedDepartament);
        Long expectedId = departamentSave.getIdDepartament();

        ResponseEntity<Departament> actualDepartamentFound = testRestTemplate.exchange(
                "/departament/{id}",
                HttpMethod.GET,
                null,
                Departament.class,
                1
        );

        assertAll(
                () -> assertNotNull(actualDepartamentFound),
                () -> assertNotNull(actualDepartamentFound.getBody().getIdDepartament()),
                () -> assertEquals(expectedId, actualDepartamentFound.getBody().getIdDepartament()),
                () -> assertEquals(HttpStatus.OK, actualDepartamentFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("findById return null departament id when departament not found")
    void findById_ReturnNullDepartament_WhenDepartamentNotFound(){
        ResponseEntity<Departament> actualDepartamentFound = testRestTemplate.exchange(
                "/departament/{id}",
                HttpMethod.GET,
                null,
                Departament.class,
                1
        );

        assertAll(
                () -> assertNull(actualDepartamentFound.getBody().getIdDepartament()),
                () -> assertEquals(HttpStatus.BAD_REQUEST, actualDepartamentFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("save insert departament in databese when successful")
    void save_InsertDepartament_WhenSuccessful(){
        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBody();

        ResponseEntity<Departament> actualDepartament = testRestTemplate.exchange(
                "/departament",
                HttpMethod.POST,
                new HttpEntity<>(departamentPostRequestBody),
                Departament.class
        );

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertEquals(HttpStatus.CREATED, actualDepartament.getStatusCode())
        );
    }

    @Test
    @DisplayName("save statuscode 400 BAD REQUEST when not successful")
    void save_StatusCodeBadRequest_WhenNotSuccessful(){
        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBodyIncorrect();

        ResponseEntity<Departament> actualDepartament = testRestTemplate.exchange(
                "/departament",
                HttpMethod.POST,
                new HttpEntity<>(departamentPostRequestBody),
                Departament.class
        );

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertEquals(HttpStatus.BAD_REQUEST, actualDepartament.getStatusCode())
        );
    }

    @Test
    @DisplayName("replace updated departament when successful")
    void replace_UpdatedDepartament_WhenSuccessful(){
        Departament save = departamentRepository.save(expectedDepartament);
        DepartamentPutRequestBody departamentPutRequestBody = DepartamentPutRequestBodyCreator.createdDepartamentPutRequestBody();
        departamentPutRequestBody.setIdDepartament(3L);

        ResponseEntity<Void> actualDepartamentReplaced = testRestTemplate.exchange(
                "/departament",
                HttpMethod.PUT,
                new HttpEntity<>(departamentPutRequestBody),
                Void.class
        );

        assertAll(
                () -> assertNotNull(actualDepartamentReplaced),
                () -> assertEquals(HttpStatus.NO_CONTENT, actualDepartamentReplaced.getStatusCode())
        );
    }

    @Test
    @DisplayName("replace statuscode 400 BAD REQUEST when not successfull")
    void replace_StatusCodeBadRequest_WhenNotSuccessful(){
        DepartamentPutRequestBody departamentPutRequestBody = DepartamentPutRequestBodyCreator.createdDepartamentPutRequestBody();

        ResponseEntity<Void> actualDepartamentReplaced = testRestTemplate.exchange(
                "/departament",
                HttpMethod.PUT,
                new HttpEntity<>(departamentPutRequestBody),
                Void.class
        );

        assertAll(
                () -> assertNotNull(actualDepartamentReplaced),
                () -> assertEquals(HttpStatus.BAD_REQUEST, actualDepartamentReplaced.getStatusCode())
        );
    }

    @Test
    @DisplayName("delete remove departament when successfull")
    void delete_RemoveDepartament_WhenSuccessful(){
        Departament save = departamentRepository.save(expectedDepartament);

        ResponseEntity<Void> actualDepartamentDeleted = testRestTemplate.exchange(
                "/departament/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                save.getIdDepartament()
        );

        assertAll(
                () -> assertNotNull(actualDepartamentDeleted),
                () -> assertEquals(HttpStatus.NO_CONTENT, actualDepartamentDeleted.getStatusCode())
        );
    }
    @Test
    @DisplayName("delete statuscode 400 BAD REQUEST when not successfull")
    void delete_StatusCodeBadRequest_WhenNotSuccessful(){
        ResponseEntity<Void> actualDepartamentDeleted = testRestTemplate.exchange(
                "/departament/{id}",
                HttpMethod.DELETE,
                null,
                Void.class,
                expectedDepartament.getIdDepartament()
        );

        assertAll(
                () -> assertNotNull(actualDepartamentDeleted),
                () -> assertEquals(HttpStatus.BAD_REQUEST, actualDepartamentDeleted.getStatusCode())
        );
    }
}