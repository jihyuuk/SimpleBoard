package com.simple.board.service;

import com.simple.board.domain.entity.User;
import com.simple.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Transactional
    public void changePassword(Long id, String newPassword){
        User user = userRepository.findById(id).get();
        user.changePassword(newPassword);
    }

    @Transactional
    public void changeName(Long id, String newName){
        User user = userRepository.findById(id).get();
        user.setName(newName);
    }

    @Transactional
    public void changePicture(Long id, String newPicture){
        User user = userRepository.findById(id).get();
        user.setProfile_picture(newPicture);
    }

    @Transactional
    public void withdrawal(Long id){
        User user = userRepository.findById(id).get();
        user.withdrawal();
    }

}
