package com.projetinho.userDept.service;

import com.projetinho.userDept.model.User;
import com.projetinho.userDept.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("User ID not found"));
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void replace(User user) {
        userRepository.save(user);
    }

    public void delete(Long id) throws Exception {
        User user = findById(id);
        userRepository.delete(user);
    }

}
