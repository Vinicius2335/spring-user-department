package com.projetinho.userDept.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentPutRequest {
    @NotNull
    @NotEmpty
    private String name;
    private Long id;
}
