package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class UserPostRequestBody {
    @NotNull(message = "The user name cannot be null")
    @NotEmpty(message = "The user name cannot be empty")
    @Schema(description = "This is the User name, dont acept value null or empty", required = true)
    private String name;

    @Schema(description = "This is the User email", example = "example@gmail.com")
    private String email;

    private Departament departament;
}
