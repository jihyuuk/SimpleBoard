package com.simple.board.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.simple.board.domain.entity.QUser;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.user.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootTest
public class QuerydslTest {

    @Autowired
    EntityManager em;
    @Autowired
    UserRepository userRepository;
    JPAQueryFactory query;


    @Test
    void contextLoads(){
        query = new JPAQueryFactory(em);
        //querydsl과 repository의 findAll값 비교
        List<User> userList = query
                .selectFrom(QUser.user)
                .fetch();
        Assertions.assertThat(userList.size()).isEqualTo(userRepository.findAll().size());
    }
}
