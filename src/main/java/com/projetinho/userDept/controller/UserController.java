package com.projetinho.userDept.controller;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.repository.UserRepository;
import com.projetinho.userDept.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws Exception {
        return new ResponseEntity<>(userService.findById(id).orElseThrow(() -> new Exception("User ID not Found")), HttpStatus.OK);
    }

    // TODO CRIAR INSERIR VALIDATION, MAPPER, DTO
    @PostMapping
    public void save(@RequestBody User user){
        userService.save(user);
    }
}
