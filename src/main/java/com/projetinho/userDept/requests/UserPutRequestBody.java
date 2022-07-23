package com.projetinho.userDept.requests;

import com.projetinho.userDept.model.Departament;
import lombok.Data;

@Data
public class UserPutRequestBody {
    private Long idUser;
    private String name;
    private String email;
    private Departament departament;
}
