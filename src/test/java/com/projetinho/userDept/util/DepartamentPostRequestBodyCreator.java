package com.projetinho.userDept.util;

import com.projetinho.userDept.requests.DepartamentPostRequestBody;

public class DepartamentPostRequestBodyCreator {
    public static DepartamentPostRequestBody createdDepartamentPostRequestBody(){
        return DepartamentPostRequestBody.builder()
                .name(DepartamentCreator.createdDepartamentForSave().getName())
                .build();
    }

    public static DepartamentPostRequestBody createdDepartamentPostRequestBodyIncorrect(){
        return DepartamentPostRequestBody.builder().build();
    }
}
