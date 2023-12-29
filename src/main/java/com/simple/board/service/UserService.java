package com.simple.board.service;

import com.simple.board.domain.entity.User;
import com.simple.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long join(String email, String password, String name){
        //이메일 인증 하기(추후 구현)
        //닉네임 중복 체크(추후 구현)
        User user = new User(email, password, name);
        userRepository.save(user);
        return user.getId();
    }

    public User findById(Long id){
        return userRepository.findById(id).get();
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

}
