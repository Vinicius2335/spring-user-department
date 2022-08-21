package com.projetinho.userDept.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentPostRequest {
    @NotNull(message = "The departament name cannot be null")
    @NotEmpty(message = "The departament name cannot be empty")
    @Schema(description = "This is the departament name, dont acept value null or empty", required = true)
    private String name;
}
