package com.simple.board.domain.entity;

import com.simple.board.domain.enums.ROLE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.*;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class UserTest {

    @Autowired
    EntityManager em;


//    Long id;
//    String email;
//    String password;
//    String picture;
//    String name;
//    role;
//    enabled;
//    createdDate;
//    LastModifiedDate;
//    deletedDate;

    User user;

    @BeforeEach
    void user(){
        user = new User("abc@gmail.com","123123","홍길동");
    }

    @Test
    void userInit(){
        //save 하기전
        //따라서 id, baseTime null임

        assertThat(user.getId()).isNull();
        assertThat(user.getLastModifiedDate()).isNull();
        assertThat(user.getDeletedDate()).isNull();
        assertThat(user.getCreatedDate()).isNull();

        assertThat(user.getEmail()).isEqualTo("abc@gmail.com");
        assertThat(user.getPassword()).isEqualTo("123123");
        assertThat(user.getName()).isEqualTo("홍길동");

        //picture,role,enabled 초기화 잘 되었는지 확인
        assertThat(user.isEnabled()).isTrue();
        assertThat(user.getRole()).isEqualTo(ROLE.ROLE_USER);
        assertThat(user.getPicture()).isEqualTo("default/picture");
    }

    @Test
    void save(){
        //1.save()시에 id값 들어갔나 확인
        //2.영속성 초기화 후 findById로 확인하기
        em.persist(user);
        em.flush();

        //#1
        Long userId = user.getId();
        assertThat(userId).isNotNull();

        em.clear();

        //#2
        User findUser = em.find(User.class, userId);
        assertThat(findUser.name).isEqualTo("홍길동");

    }

}