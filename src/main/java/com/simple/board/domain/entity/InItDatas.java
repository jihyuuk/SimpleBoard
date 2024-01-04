package com.simple.board.domain.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class InItDatas {

    @PersistenceContext
    EntityManager em;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public void init(){
        User user = new User("aaa@gmail.com", bCryptPasswordEncoder.encode("123123"), "userA");
        User userB = new User("bbb@gmail.com", bCryptPasswordEncoder.encode("123123"), "userB");
        User userC = new User("ccc@gmail.com", bCryptPasswordEncoder.encode("123123"), "userC");
        User userJ = new User("jihyuk8778@gmail.com", bCryptPasswordEncoder.encode("123123"), "나이키슈즈");
        em.persist(user);
        em.persist(userB);
        em.persist(userC);
        em.persist(userJ);

        Category category = new Category("커뮤니티");
        Category category2 = new Category("Q&A");
        Category category3 = new Category("공지사항");
        Category category4 = new Category("BEST");
        em.persist(category);
        em.persist(category2);
        em.persist(category3);
        em.persist(category4);

        Content content = new Content("모든 국민은 법률이 정하는 바에 의하여 선거권을 가진다. 선거에 관한 경비는 법률이 정하는 경우를 제외하고는 정당 또는 후보자에게 부담시킬 수 없다.");
        em.persist(content);

        Post post = new Post(category,user,"제목제목제제목",content);
        em.persist(post);

        Reply reply1 = new Reply(post, userB, "현재 국비과정 뭐시기 저시기");
        Reply reply2 = new Reply(post,userC,"힘내십쇼!");
        em.persist(reply1);
        em.persist(reply2);
    }
}
