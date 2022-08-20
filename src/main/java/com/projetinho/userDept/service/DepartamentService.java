package com.projetinho.userDept.service;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.mapper.DepartamentMapper;
import com.projetinho.userDept.model.Departament;
import com.projetinho.userDept.repository.DepartamentRepository;
import com.projetinho.userDept.requests.DepartamentPostRequest;
import com.projetinho.userDept.requests.DepartamentPutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartamentService {
    private final DepartamentRepository departamentRepository;

    public List<Departament> findAll(){
        return departamentRepository.findAll();
    }

    public Departament findByIdOrThrowsBadRequestException(Long id){
        return departamentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Departament Id not found"));
    }

    public void save(DepartamentPostRequest departament){
        Departament toDepartament = DepartamentMapper.INSTANCE.toDepartament(departament);
        departamentRepository.save(toDepartament);
    }

    public void replace(DepartamentPutRequest departament){
        Departament toDepartament = DepartamentMapper.INSTANCE.toDepartament(departament);
        Departament replacedDepartament = findByIdOrThrowsBadRequestException(toDepartament.getIdDepartament());
         departamentRepository.save(replacedDepartament);
    }

    public void delete(Long id){
        Departament departament = findByIdOrThrowsBadRequestException(id);
        departamentRepository.delete(departament);
    }
}
