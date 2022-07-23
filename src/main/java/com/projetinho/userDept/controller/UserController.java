package com.projetinho.userDept.controller;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
     return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    // TODO CRIAR NOSSA PROPRIA EXCEPTION
    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(userService.findById(id));
    }

    // TODO CRIAR INSERIR VALIDATION, MAPPER, DTO
    // TODO nao está funcionando

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) throws Exception {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // TODO nao está funcionando
    @PutMapping
    public ResponseEntity<User> replace(@RequestBody User user) throws Exception {
        userService.replace(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<User> delete(@PathVariable Long id) throws Exception {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
