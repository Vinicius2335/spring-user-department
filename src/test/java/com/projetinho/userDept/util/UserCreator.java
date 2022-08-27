package com.projetinho.userDept.util;

import com.projetinho.userDept.model.User;

public class UserCreator {
    public static User createdUserForSave(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentValid())
                .email("vinicius@gmailcom")
                .name("Vinicius")
                .build();
    }

    public static User createdUserValid(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentValid())
                .email("vinicius@gmailcom")
                .idUser(1L)
                .name("Vinicius")
                .build();
    }

    public static User createUserForUpdate(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentForUpdate())
                .email("update@gmailcom")
                .idUser(1L)
                .name("Update")
                .build();
    }
}
