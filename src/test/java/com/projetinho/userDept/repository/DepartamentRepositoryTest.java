package com.projetinho.userDept.repository;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.util.DepartamentCreator;
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
@DisplayName("Test for Departament Repository")
class DepartamentRepositoryTest {
    @Autowired
    private DepartamentRepository departamentRepository;
    @Autowired
    private UserRepository userRepository;

    private Departament departamentToSave;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        departamentRepository.deleteAll();
        departamentToSave = DepartamentCreator.createdDepartamentForSave();
    }

    @Test
    @DisplayName("Save persists departament when successful ")
    void save_PersistsDepartament_WhenSuccessful() {
        Departament actualDepartament = departamentRepository.save(departamentToSave);

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertNotNull(actualDepartament.getIdDepartament()),
                () -> assertEquals(departamentToSave.getName(), actualDepartament.getName())
        );
    }

    @Test
    @DisplayName("save update departament when successful")
    void save_UpdateDepartament_WhenSuccessful() {
        departamentRepository.save(departamentToSave);

        Departament departamentForUpdate = DepartamentCreator.createdDepartamentForUpdate();
        Departament actualDepartament = departamentRepository.save(departamentForUpdate);

        assertAll(
                () -> assertNotNull(actualDepartament),
                () -> assertNotNull(actualDepartament.getIdDepartament()),
                () -> assertEquals(departamentForUpdate.getName(), actualDepartament.getName())
        );
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when name is empty")
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Departament departament = new Departament();

        assertThrows(ConstraintViolationException.class, () -> departamentRepository.save(departament),
                "The departament name cannot be empty");
    }

    @Test
    @DisplayName("FindAll return list departament when successful")
    void findAll_ReturnListDepartament_WhenSuccessful() {
        Departament actualDepartament = departamentRepository.save(departamentToSave);
        List<Departament> actualDepartamentList = departamentRepository.findAll();

        assertAll(
                () -> assertNotNull(actualDepartamentList),
                () -> assertTrue(actualDepartamentList.contains(actualDepartament))
        );
    }

    @Test
    @DisplayName("FindById return departement by id when successful")
    void findById_ReturnDepartament_WhenSuccessful() {
        Departament expectedDepartament = departamentRepository.save(departamentToSave);
        Departament departamentFound = departamentRepository.findById(expectedDepartament.getIdDepartament()).get();

        assertAll(
                () -> assertNotNull(departamentFound),
                () -> assertEquals(expectedDepartament.getIdDepartament(), departamentFound.getIdDepartament())
        );
    }

    @Test
    @DisplayName("Delete departament when successful")
    void delete_RemoveDepartament_WhenSuccessful() {
        Departament actualDepartament = departamentRepository.save(departamentToSave);
        departamentRepository.delete(actualDepartament);
        Optional<Departament> departamentList = departamentRepository.findById(actualDepartament.getIdDepartament());

        assertTrue(departamentList.isEmpty());
    }

}