package com.projetinho.userDept.openapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Departaments")
public interface DepartamentControllerOpenApi {

	@Operation(summary = "List All Departament", description = "List all Departament in the database")
	ResponseEntity<List<Departament>> listAll();
	
	
    @Operation(summary = "Find the Departament By Id", description = "Return the departament by id in the database",
    		responses = {
    				@ApiResponse(responseCode = "200", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When departement not founded by id in the datavase", 
    	            		content = @Content(schema = @Schema(ref = "ExceptionDetails")))
    		})
	ResponseEntity<Departament> listById(@Parameter(description = "ID de um Departament", example = "1",
			required = true) Long id);
    
    
    @Operation(summary = "Save Departament", description = "Insert the departament in the database",
    		responses = {
    				@ApiResponse(responseCode = "201", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When the departament not saved in the database", 
    	            		content = @Content(schema = @Schema(ref = "ExceptionDetails")))
    		})
    ResponseEntity<Departament> save(@RequestBody(description = "Representação de um novo Departament",
    		required = true) DepartamentPostRequestBody departament);
    
    
    @Operation(summary = "Replace Departament", description = "Update the departament in the database",
    		responses = {
    				@ApiResponse(responseCode = "204", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When the departament not exists in the database",
    	            		content = @Content(schema = @Schema(ref = "ExceptionDetails")))
    		})
    ResponseEntity<Void> replace(@RequestBody(description = "Representação de um Departament com dados atualizados",
    		required = true) DepartamentPutRequestBody departament);
    
    
    @Operation(summary = "Delete Departament", description = "Delete the departament in the database",
    		responses = {
    				@ApiResponse(responseCode = "204", description = "Successful Operation"),
    	            @ApiResponse(responseCode = "400", description = "When the departament not exists in the database",
    	            		content = @Content(schema = @Schema(ref = "ExceptionDetails")))	
    		})
    ResponseEntity<Void> delete(@Parameter(description = "ID de um Departament", example = "1",
			required = true) Long id);
}
