package com.projetinho.userDept.repository;

import com.projetinho.userDept.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
