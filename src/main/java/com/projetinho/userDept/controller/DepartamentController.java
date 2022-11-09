package com.projetinho.userDept.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.openapi.controller.DepartamentControllerOpenApi;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import com.projetinho.userDept.service.DepartamentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("departament")
@RequiredArgsConstructor
public class DepartamentController implements DepartamentControllerOpenApi {
    private final DepartamentService departamentService;

    @Override
    @GetMapping
    public ResponseEntity<List<Departament>> listAll(){
        return new ResponseEntity<>(departamentService.findAll(), HttpStatus.OK);
    }

    @Override
    @GetMapping(path = "/{id}")
    public ResponseEntity<Departament> listById(@PathVariable Long id){
        return new ResponseEntity<>(departamentService.findByIdOrThrowsBadRequestException(id), HttpStatus.OK);
    }

    @Override
    @PostMapping
    public ResponseEntity<Departament> save(@RequestBody @Valid DepartamentPostRequestBody departament){
        departamentService.save(departament);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody @Valid DepartamentPutRequestBody departament){
        departamentService.replace(departament);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        departamentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
