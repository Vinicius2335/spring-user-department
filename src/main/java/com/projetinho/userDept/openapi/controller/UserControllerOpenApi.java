package com.projetinho.userDept.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Users")
public interface UserControllerOpenApi {

	@Operation(summary = "Find all User", description = "List all users in given database")
	ResponseEntity<List<User>> findAll();
	
	
    @Operation(summary = "Find By Id User", description = "Return the user founded by id",
    		responses = {
    				@ApiResponse(responseCode = "200", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When user dont exists in database",
    	            content = @Content(schema = @Schema(ref = "ExceptionDetails")))
    		})
	ResponseEntity<User> findById(@Parameter(description = "Id de um user", example = "1", required = true) Long id);
    
    
    @Operation(summary = "Find By Name User", description = "List all user with the name in the given database")
    ResponseEntity<List<User>> findByName(@Parameter(description = "Nome de um user", example = "João",
    		required = true) String name);
    
    
    @Operation(summary = "Save User", description = "Insert the user in the database",
    		responses = {
    				@ApiResponse(responseCode = "201", description = "Successful Operation"),
    				@ApiResponse(responseCode = "400", description = "When user not save in the database",
    				content = @Content(schema = @Schema(ref = "ExceptionDetails")))
    })
    ResponseEntity<User> save(@RequestBody(description = "Represenção de um novo user", required = true)
    		UserPostRequestBody user);
    
    
    @Operation(summary = "Replace User", description = "Update the user in the database",
    		responses = {
    				@ApiResponse(responseCode = "204", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When the user not founded in the database for replace",
    	            content = @Content(schema = @Schema(ref = "ExceptionDetails")))	
    		})
    ResponseEntity<Void> replace(@RequestBody(description = "Representação de um user com dados atualizados",
    		required = true) UserPutRequestBody user);
    
    
    @Operation(summary = "Delete User", description = "Delete the user in the database",
    		responses = {
    				@ApiResponse(responseCode = "204", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When the user not founded in the database", 
    	            content = @Content(schema = @Schema(ref = "ExceptionDetails")))	
    		})
    ResponseEntity<Void> delete(@Parameter(description = "Id de um user", example = "1", required = true) Long id);
}
