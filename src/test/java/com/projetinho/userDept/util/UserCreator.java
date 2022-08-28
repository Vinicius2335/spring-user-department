package com.projetinho.userDept.util;

import com.projetinho.userDept.model.User;

public class UserCreator {
    public static User createdUserForSave(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentValid())
                .email("vinicius@gmail.com")
                .name("Vinicius")
                .build();
    }

    public static User createdUserValid(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentValid())
                .email("vinicius@gmail.com")
                .idUser(1L)
                .name("Vinicius")
                .build();
    }

    public static User createUserForUpdate(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentForUpdate())
                .email("update@gmail.com")
                .idUser(1L)
                .name("Update")
                .build();
    }
    public static User createUserNotFound(){
        return User.builder()
                .departament(DepartamentCreator.createdDepartamentValid())
                .email("notfound@gmail.com")
                .idUser(999L)
                .name("NotFound")
                .build();
    }
}
