package com.projetinho.userDept.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class DepartamentPostRequestBody {
    @NotNull(message = "The departament name cannot be null")
    @NotEmpty(message = "The departament name cannot be empty")
    @Schema(description = "This is the departament name, dont acept value null or empty", required = true)
    private String name;
}
