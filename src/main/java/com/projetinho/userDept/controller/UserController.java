package com.projetinho.userDept.controller;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;
import com.projetinho.userDept.service.UserService;
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
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Find all User", description = "List all users in given database", tags = {"User"})
    public ResponseEntity<List<User>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Find By Id User", description = "Return the user founded by id", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When user dont exists in database")
    })
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/find")
    @Operation(summary = "Find By Name User", description = "List all user with the name in the given database",
            tags = {"User"})
    public ResponseEntity<List<User>> findByName(@RequestParam String name) {
        return new ResponseEntity<>(userService.findByName(name), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Save User", description = "Insert the user in the database", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When user not save in the database")
    })
    public ResponseEntity<User> save(@RequestBody @Valid UserPostRequestBody user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Operation(summary = "Replace User", description = "Update the user in the database", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the user not founded in the database for replace")
    })
    public ResponseEntity<Void> replace(@RequestBody UserPutRequestBody user) {
        userService.replace(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete User", description = "Delete the user in the database", tags = {"User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the user not founded in the database")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
