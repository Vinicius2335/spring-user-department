package com.projetinho.userDept.util;

import com.projetinho.userDept.model.Departament;

public class DepartamentCreator {
    public static Departament createdDepartamentForSave(){
       return Departament.builder()
               .name("Gestao")
               .build();
    }

    public static Departament createdDepartamentValid(){
        return Departament.builder()
                .idDepartament(1L)
                .name("Gestao")
                .build();
    }

    public static Departament createdDepartamentForUpdate(){
        return Departament.builder()
                .idDepartament(1L)
                .name("Update")
                .build();
    }
}
