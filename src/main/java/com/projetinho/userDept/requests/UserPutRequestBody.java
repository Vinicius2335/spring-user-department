package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPutRequestBody {
    private Long idUser;
    private String name;
    private String email;
    private Departament departament;
}
