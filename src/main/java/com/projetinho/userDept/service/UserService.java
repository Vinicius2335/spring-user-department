package com.projetinho.userDept.service;

import com.projetinho.userDept.exception.BadRequestException;
import com.projetinho.userDept.mapper.UserMapper;
import com.projetinho.userDept.model.User;
import com.projetinho.userDept.repository.UserRepository;
import com.projetinho.userDept.requests.UserPostRequestBody;
import com.projetinho.userDept.requests.UserPutRequestBody;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("User ID not found"));
    }

    public void save(UserPostRequestBody userPostRequestBodyser){
        User user = UserMapper.INSTANCE.toUser(userPostRequestBodyser);
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public void replace(UserPutRequestBody userPutRequestBody) {
        User userFindind = findById(userPutRequestBody.getIdUser());
        User user = UserMapper.INSTANCE.toUser(userPutRequestBody);

        user.setIdUser(userFindind.getIdUser());
        userRepository.save(user);
    }

    public void delete(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

}
