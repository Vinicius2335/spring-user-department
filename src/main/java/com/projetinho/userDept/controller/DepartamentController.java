package com.projetinho.userDept.controller;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequest;
import com.projetinho.userDept.requests.DepartamentPutRequest;
import com.projetinho.userDept.service.DepartamentService;
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
    public ResponseEntity<List<Departament>> listAll(){
        return new ResponseEntity<>(departamentService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Departament> listById(@PathVariable Long idDepartament){
        return new ResponseEntity<>(departamentService.findByIdOrThrowsBadRequestException(idDepartament), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Departament>  save(@RequestBody @Valid DepartamentPostRequest departament){
        departamentService.save(departament);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Departament> replace(@RequestBody @Valid DepartamentPutRequest departament){
        departamentService.replace(departament);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Departament> delete(@PathVariable Long idDepartament){
        departamentService.delete(idDepartament);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
