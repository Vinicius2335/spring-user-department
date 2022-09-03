package com.projetinho.userDept.controller;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.mapper.DepartamentMapper;
import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import com.projetinho.userDept.service.DepartamentService;
import com.projetinho.userDept.util.DepartamentCreator;
import com.projetinho.userDept.util.DepartamentPostRequestBodyCreator;
import com.projetinho.userDept.util.DepartamentPutRequestBodyCreator;
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
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Test for departament controller")
class DepartamentControllerTest {
    @InjectMocks
    private DepartamentController departamentController;
    @Mock
    private DepartamentService departamentServiceMock;
    private Departament expectedDepartament;
    private List<Departament> expectedDepartamentList;

    @BeforeEach
    void setUp() {
        expectedDepartament = DepartamentCreator.createdDepartamentValid();
        expectedDepartamentList = List.of(expectedDepartament);
        
        // listAll
        BDDMockito.when(departamentServiceMock.findAll()).thenReturn(expectedDepartamentList);

        // findById
        BDDMockito.when(departamentServiceMock.findByIdOrThrowsBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(expectedDepartament);

        // save
        BDDMockito.when(departamentServiceMock.save(ArgumentMatchers.any(DepartamentPostRequestBody.class)))
                .thenReturn(expectedDepartament);

        // replace
        BDDMockito.doNothing().when(departamentServiceMock)
                .replace(ArgumentMatchers.any(DepartamentPutRequestBody.class));

        // delete
        BDDMockito.doNothing().when(departamentServiceMock).delete(ArgumentMatchers.anyLong());
    }
    
    @Test
    @DisplayName("listAll return list departament when successful")
    void listAll_ReturnListDepartament_WhenSuccessful(){
        ResponseEntity<List<Departament>> actualDepartamentList = departamentController.listAll();

        assertAll(
                () -> assertNotNull(actualDepartamentList),
                () -> assertEquals(expectedDepartamentList, actualDepartamentList.getBody()),
                () -> assertEquals(HttpStatus.OK, actualDepartamentList.getStatusCode())
        );
    }

    @Test
    @DisplayName("listById return departament found by id when successful")
    void listById_ReturnDepartament_WhenIdFoundSuccessfull(){
        ResponseEntity<Departament> actualDepartamentFound = departamentController.listById(expectedDepartament.getIdDepartament());

        assertAll(
                () -> assertNotNull(actualDepartamentFound),
                () -> assertEquals(expectedDepartament, actualDepartamentFound.getBody()),
                () -> assertEquals(HttpStatus.OK, actualDepartamentFound.getStatusCode())
        );
    }

    @Test
    @DisplayName("listById throws BadRequestException when departament not found")
    void listById_ThrowBadRequest_WhenDepartamentNotFound(){
        BDDMockito.when(departamentServiceMock.findByIdOrThrowsBadRequestException(ArgumentMatchers.anyLong()))
                .thenThrow(BadRequestException.class);

        assertThrows(BadRequestException.class, () -> departamentController.listById(99L));
    }

    @Test
    @DisplayName("save insert departament when successful")
    void save_InsertDepartament_WhenSuccessful(){
        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBody();

        ResponseEntity<Departament> actualDepartament = departamentController.save(departamentPostRequestBody);

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertEquals(HttpStatus.CREATED, actualDepartament.getStatusCode())
        );
    }

    @Test
    @DisplayName("save Throws ConstraintViolationException When Departament Not Save")
    void save_ThrowsBadRequestException_WhenDepartamentNotSave(){
        BDDMockito.when(departamentServiceMock.save(ArgumentMatchers.any(DepartamentPostRequestBody.class)))
                .thenThrow(ConstraintViolationException.class);

        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBodyIncorrect();

        assertThrows(ConstraintViolationException.class, () -> departamentController.save(departamentPostRequestBody));
    }

    @Test
    @DisplayName("replace update departament when successful")
    void replace_UpdateDepartament_WhenSuccessful(){
        DepartamentPutRequestBody departamentPutRequestBody = DepartamentPutRequestBodyCreator.createdDepartamentPutRequestBody();

        assertDoesNotThrow(() -> departamentController.replace(departamentPutRequestBody));

        ResponseEntity<Void> actualDepartament = departamentController.replace(departamentPutRequestBody);

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertEquals(HttpStatus.NO_CONTENT, actualDepartament.getStatusCode())
        );
    }

    @Test
    @DisplayName("delete remove departament when successful")
    void delete_RemoveDepartament_WhenSuccessful(){
        assertDoesNotThrow(() -> departamentController.delete(1L));

        ResponseEntity<Void> actualDepartament = departamentController.delete(1L);

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertEquals(HttpStatus.NO_CONTENT, actualDepartament.getStatusCode())
        );
    }
}