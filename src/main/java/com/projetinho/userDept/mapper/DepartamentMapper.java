package com.projetinho.userDept.mapper;

import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.requests.DepartamentPostRequestBody;
import com.projetinho.userDept.requests.DepartamentPutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class DepartamentMapper {
    public static final DepartamentMapper INSTANCE = Mappers.getMapper(DepartamentMapper.class);

    public abstract Departament toDepartament(DepartamentPostRequestBody departamentPostRequestBody);
    public abstract Departament toDepartament(DepartamentPutRequestBody departamentPutRequestBody);
}
