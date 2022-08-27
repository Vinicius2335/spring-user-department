package com.projetinho.userDept.service;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.repository.DepartamentRepository;
import com.projetinho.userDept.repository.UserRepository;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import com.projetinho.userDept.util.DepartamentCreator;
import com.projetinho.userDept.util.DepartamentPostRequestBodyCreator;
import com.projetinho.userDept.util.DepartamentPutRequestBodyCreator;
import org.junit.jupiter.api.AfterEach;
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
@DisplayName("Test for Departament Service")
class DepartamentServiceTest {
    @InjectMocks
    private DepartamentService departamentService;
    @Mock
    private DepartamentRepository departamentRepositoryMock;
    private Departament expectedDepartament;
    private List<Departament> expectedListDepartament;


    @BeforeEach
    void setUp() {
        expectedDepartament = DepartamentCreator.createdDepartamentValid();
        expectedListDepartament = List.of(expectedDepartament);

        // listAll
        BDDMockito.when(departamentRepositoryMock.findAll())
                .thenReturn(expectedListDepartament);

        // findById
        BDDMockito.when(departamentRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(expectedDepartament));

        // save
        BDDMockito.when(departamentRepositoryMock.save(ArgumentMatchers.any(Departament.class)))
                .thenReturn(expectedDepartament);

        // delete
        BDDMockito.doNothing().when(departamentRepositoryMock)
                .delete(ArgumentMatchers.any(Departament.class));

    }

    @Test
    @DisplayName("list all return list departament when successful")
    void listAll_ReturnListDepartament_WhenSuccessful() {
        List<Departament> actualDepartamentListFound = departamentService.findAll();

        Long expectedIdDepartament = DepartamentCreator.createdDepartamentValid().getIdDepartament();

        assertAll(
                () -> assertNotNull(actualDepartamentListFound),
                () -> assertEquals(expectedListDepartament, actualDepartamentListFound),
                () -> assertEquals(1, actualDepartamentListFound.size()),
                () -> assertEquals(expectedIdDepartament, actualDepartamentListFound.get(0).getIdDepartament())
        );
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException return departament when successful")
    void findByIdOrThrowsBadRequestException_ReturnDepartament_WhenSuccessful() {
        Long expectedId = DepartamentCreator.createdDepartamentValid().getIdDepartament();
        Departament actualDepartamentFound = departamentService.findByIdOrThrowsBadRequestException(expectedId);

        assertAll(
                () -> assertNotNull(actualDepartamentFound),
                () -> assertEquals(expectedDepartament, actualDepartamentFound),
                () -> assertEquals(expectedId, actualDepartamentFound.getIdDepartament())
        );
    }

    @Test
    @DisplayName("findByIdOrThrowsBadRequestException throw BadRequestException when id departament not found")
    void findByIdOrThrowsBadRequestException_ThrowsBadRequest_WhenIdDepartamentNotFounded() {
        BDDMockito.when(departamentRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, () -> departamentService.findByIdOrThrowsBadRequestException(1L));
    }

    @Test
    @DisplayName("save return departament when successful")
    void save_ReturnDepartament_WhenSuccessful() {
        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBody();
        Departament actualDepartament = departamentService.save(departamentPostRequestBody);

        assertEquals(expectedDepartament, actualDepartament);
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        BDDMockito.when(departamentRepositoryMock.save(ArgumentMatchers.any(Departament.class)))
                .thenThrow(ConstraintViolationException.class);

        DepartamentPostRequestBody departamentPostRequestBody = DepartamentPostRequestBodyCreator.createdDepartamentPostRequestBodyIncorrect();

        assertThrows(ConstraintViolationException.class, () -> departamentService.save(departamentPostRequestBody),
                "The departament name cannot be empty");
    }

    @Test
    @DisplayName("replace updated departamanet when successful")
    void replace_UpdateDepartament_WhenSuccessful() {
        DepartamentPutRequestBody departamentPutRequestBody = DepartamentPutRequestBodyCreator.createdDepartamentPutRequestBody();

        assertDoesNotThrow(() -> departamentService.replace(departamentPutRequestBody));
    }

    @Test
    @DisplayName("delete remove departament when successful")
    void delete_RemoveDepartament_WhenSuccessful() {
        assertDoesNotThrow(() -> departamentService.delete(1L));
    }

}