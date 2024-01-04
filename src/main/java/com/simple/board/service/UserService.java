package com.simple.board.service;

import com.simple.board.domain.entity.User;
import com.simple.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long join(String email, String password, String name){
        //이메일 인증 하기(추후 구현)
        //닉네임 중복 체크(추후 구현)
        User user = new User(email, bCryptPasswordEncoder.encode(password), name);
        userRepository.save(user);
        return user.getId();
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

}
