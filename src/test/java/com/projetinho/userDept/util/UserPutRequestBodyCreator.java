package com.projetinho.userDept.util;

import com.projetinho.userDept.requests.UserPutRequestBody;

public class UserPutRequestBodyCreator {
    public static UserPutRequestBody createUserPutRequestBody(){
        return UserPutRequestBody.builder()
                .departament(UserCreator.createUserForUpdate().getDepartament())
                .email(UserCreator.createUserForUpdate().getEmail())
                .idUser(UserCreator.createUserForUpdate().getIdUser())
                .name(UserCreator.createUserForUpdate().getName())
                .build();
    }
}
