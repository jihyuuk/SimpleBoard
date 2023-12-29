package com.simple.board.service;

import com.simple.board.domain.entity.User;
import com.simple.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }

}
