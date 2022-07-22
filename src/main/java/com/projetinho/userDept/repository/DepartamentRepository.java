package com.projetinho.userDept.repository;

import com.projetinho.userDept.model.Departament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentRepository extends JpaRepository<Departament, Long> {
}
