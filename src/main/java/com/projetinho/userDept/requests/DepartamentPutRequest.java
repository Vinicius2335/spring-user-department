package com.projetinho.userDept.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentPutRequest {

    @NegativeOrZero(message = "The departament id cannot be Zero or Negative")
    @NotNull(message = "The departament id cannot be null")
    @Schema(description = "This is the Departament ID, Only acept Numbers greater or equals than 1", example = "1", required = true)
    private Long idDepartament;

    @NotNull(message = "The departament name cannot be null")
    @NotEmpty(message = "The departament name cannot be empty")
    @Schema(description = "This is the Departament Name, dont acept value null or empty", example = "Naruto", required = true)
    private String name;
}
