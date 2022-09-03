package com.projetinho.userDept.controller;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import com.projetinho.userDept.service.DepartamentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("departament")
@RequiredArgsConstructor
public class DepartamentController {
    private final DepartamentService departamentService;

    @GetMapping
    @Operation(summary = "List All Departament", description = "List all Departament in the database", tags = {"Departament"})
    public ResponseEntity<List<Departament>> listAll(){
        return new ResponseEntity<>(departamentService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find the Departament By Id", description = "Return the departament by id in the database", tags = {"Departament"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When departement not founded by id in the datavase")
    })
    public ResponseEntity<Departament> listById(@PathVariable Long id){
        return new ResponseEntity<>(departamentService.findByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Save Departament", description = "Insert the departament in the database", tags = {"Departament"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the departament not saved in the database")
    })
    public ResponseEntity<Departament> save(@RequestBody @Valid DepartamentPostRequestBody departament){
        departamentService.save(departament);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Replace Departament", description = "Update the departament in the database", tags = {"Departament"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the departament not exists in the database")
    })
    public ResponseEntity<Void> replace(@RequestBody @Valid DepartamentPutRequestBody departament){
        departamentService.replace(departament);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete Departament", description = "Delete the departament in the database", tags = {"Departament"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the departament not exists in the database")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id){
        departamentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
