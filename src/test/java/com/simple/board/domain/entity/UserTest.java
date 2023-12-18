package com.simple.board.domain.entity;

import com.simple.board.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class UserTest {

    @Autowired
    UserService userService;
    @Autowired
    EntityManager em;

    @Test
    @DisplayName("join")
    void 회원가입(){
        //이메일 인증을 실행 한 뒤
        Long userId = userService.join("jihyuuk@gmail.com", "123123", "hong gil dong");
        User findUser = userService.findById(userId);

        assertThat(findUser.getId()).isEqualTo(userId);
        assertThat(findUser.getEmail()).isEqualTo("jihyuuk@gmail.com");
        assertThat(findUser.getName()).isEqualTo("hong gil dong");

        //기본세팅 잘 들어갔나 확인
        assertThat(findUser.getProfile_picture()).isEqualTo("기본사진");
        assertThat(findUser.getRole()).isEqualTo(Role.ROLE_USER);
        assertThat(findUser.getCreatedDate()).isNotNull();
        assertThat(findUser.getLastModifiedDate()).isNotNull();
        assertThat(findUser.getWithdrawal_date()).isNull();
        assertThat(findUser.getLast_password_changed()).isNotNull();
        assertThat(findUser.isEnabled()).isTrue();
        assertThat(findUser.isWithdrawal()).isFalse();
    }

    @Test
    @DisplayName("chageName")
    void 이름변경(){
        //이름 변경확인
        //lastModifyDate변경 확인

        Long userId = userService.join("jihyuuk@gmail.com", "123123", "hong gil dong");
        User user = userService.findById(userId);
        영속성초기화();

        userService.changeName(userId, "kim");
        영속성초기화();

        User updateUser = userService.findById(userId);

        assertThat(updateUser.getId()).isEqualTo(user.getId());
        assertThat(updateUser.getName()).isEqualTo("kim");
        assertThat(updateUser.getLastModifiedDate()).isNotEqualTo(user.getLastModifiedDate());
    }


    @Test
    @DisplayName("chagePassword")
    void 비번변경(){
        //비번 변경확인
        //lastPasswordChanged 변경
        //lastModifiedDate 변경
        Long userId = userService.join("jihyuuk@gmail.com", "123123", "hong gil dong");
        User user = userService.findById(userId);
        영속성초기화();

        userService.changePassword(userId,"3333");
        영속성초기화();

        User updateUser = userService.findById(userId);

        assertThat(updateUser.getId()).isEqualTo(user.getId());
        assertThat(updateUser.getPassword()).isEqualTo("3333");
        assertThat(updateUser.getLast_password_changed()).isNotEqualTo(user.getLast_password_changed());
        assertThat(updateUser.getLastModifiedDate()).isNotEqualTo(user.getLastModifiedDate());
    }

    @Test
    @DisplayName("chagePicture")
    void 프사변경() throws Exception{
        //프사변경 확인
        //lastModifiedDate 변경확인
        Long userId = userService.join("jihyuuk@gmail.com", "123123", "hong gil dong");
        User user = userService.findById(userId);
        영속성초기화();

        userService.changePicture(userId, "new Picture");
        영속성초기화();

        User updateUser = userService.findById(userId);

        assertThat(updateUser.getId()).isEqualTo(user.getId());
        assertThat(updateUser.getProfile_picture()).isEqualTo("new Picture");
        assertThat(updateUser.getLastModifiedDate()).isNotEqualTo(user.getLastModifiedDate());
    }

    @Test
    @DisplayName("withdrawal")
    void 탈퇴() throws Exception{
        //enabled = false;
        //withdrawal = true;
        //withdrawal_date = 삭제일;

        Long userId = userService.join("jihyuuk@gmail.com", "123123", "hong gil dong");
        User user = userService.findById(userId);
        영속성초기화();

        assertThat(user.getWithdrawal_date()).isNull();
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.isWithdrawal()).isFalse();

        userService.withdrawal(userId);
        영속성초기화();

        User updateUser = userService.findById(userId);

        assertThat(updateUser.getId()).isEqualTo(user.getId());
        assertThat(updateUser.isEnabled()).isFalse();
        assertThat(updateUser.isWithdrawal()).isTrue();
        assertThat(updateUser.getWithdrawal_date()).isNotNull();
        assertThat(updateUser.getLastModifiedDate()).isNotEqualTo(user.getLastModifiedDate());
    }

    private void 영속성초기화() {
        em.flush();
        em.clear();
    }




}