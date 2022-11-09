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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.openapi.controller.UserControllerOpenApi;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;
import com.projetinho.userDept.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController implements UserControllerOpenApi {
    private final UserService userService;

    @GetMapping
    @Override
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Override
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    @Override
    public ResponseEntity<List<User>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<User> save(@RequestBody @Valid UserPostRequestBody user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody user) {
        userService.replace(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
