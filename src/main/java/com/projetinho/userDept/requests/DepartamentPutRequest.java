package com.projetinho.userDept.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentPutRequest {

    @NegativeOrZero(message = "The departament id cannot be Zero or Negative")
    @NotNull(message = "The departament id cannot be null")
    private Long idDepartament;
    private String name;
}
