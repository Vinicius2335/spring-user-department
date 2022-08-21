package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostRequestBody {
    @NotNull(message = "The user name cannot be null")
    @NotEmpty(message = "The user name cannot be empty")
    private String name;
    private String email;

    private Departament departament;
}
