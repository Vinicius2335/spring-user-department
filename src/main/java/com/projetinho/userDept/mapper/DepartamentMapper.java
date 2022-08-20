package com.projetinho.userDept.mapper;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequest;
import com.projetinho.userDept.requests.DepartamentPutRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DepartamentMapper {
    public static final DepartamentMapper INSTANCE = Mappers.getMapper(DepartamentMapper.class);

    public abstract Departament toDepartament(DepartamentPostRequest departamentPostRequest);
    public abstract Departament toDepartament(DepartamentPutRequest departamentPutRequest);
}
