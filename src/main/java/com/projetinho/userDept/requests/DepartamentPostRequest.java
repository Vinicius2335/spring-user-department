package com.projetinho.userDept.requests;

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
    private String name;
}
