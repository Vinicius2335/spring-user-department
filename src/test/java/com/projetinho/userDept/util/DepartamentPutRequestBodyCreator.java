package com.projetinho.userDept.util;

import com.projetinho.userDept.requests.DepartamentPutRequestBody;

public class DepartamentPutRequestBodyCreator {
    public static DepartamentPutRequestBody createdDepartamentPutRequestBody(){
        return DepartamentPutRequestBody.builder()
                .idDepartament(DepartamentCreator.createdDepartamentForUpdate().getIdDepartament())
                .name(DepartamentCreator.createdDepartamentForUpdate().getName())
                .build();
    }

    public static DepartamentPutRequestBody createDepartamentPutRequestBodyNotName(){
        return DepartamentPutRequestBody.builder()
                .idDepartament(DepartamentCreator.createdDepartamentForUpdate().getIdDepartament())
                .build();
    }
}
