package com.simple.board.domain.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;


@Transactional
@SpringBootTest
class PostTest {

    //user
    //category
    //content
    //reply

    @Autowired
    EntityManager em;

    @BeforeEach
    void init(){
        User userA = new User("aaa@gmail.com", "1111", "userA");
        User userB = new User("bbb@gmail.com", "2222", "userB");
        User userC = new User("ccc@gmail.com", "3333", "userC");
        Category categoryA = new Category("categoryA");
        Category categoryB = new Category("categoryB");
        Category categoryC = new Category("categoryC");
        em.persist(userA);
        em.persist(userB);
        em.persist(userC);
        em.persist(categoryA);
        em.persist(categoryB);
        em.persist(categoryC);
    }



//    @Test
//    void post생성(){
//        //userA가 CategoryC로 게시글생성
//        Post post = new Post(categoryC, userA, "제목입니다", new Content("내용 콘텐츠입니다."));
//        em.persist(post);
//
//        //user,category잘 들어갔나 확인
//        assertThat(post.getCategory().getName()).isEqualTo("categoryC");
//        assertThat(post.getUser().getName()).isEqualTo("userA");
//        //댓글은 없으므로 empty
//        assertThat(post.getReplies()).isEmpty();
//    }

    @Test
    void 댓글1개(){
        Category category = em.createQuery("select c from Category c where c.name = :name ",Category.class)
                .setParameter("name","categoryC")
                .getSingleResult();

        User user = em.createQuery("select u from User u where u.name = :name",User.class)
                .setParameter("name","userA")
                .getSingleResult();

        Content content = new Content("내용 콘텐츠입니다.");
        em.persist(content);

        Post post = new Post(category, user, "제목입니다", content);
        em.persist(post);

        //오류발견 post가 영속성 컨텍스트에 있어서  replies 적용 x
        //해결법 : new Reply할때 post의 replies를 업데이트 해줘야함
        Reply reply = new Reply(post, user, "댓글입니다");
        em.persist(reply);

        em.flush();
        em.clear();

        assertThat(em.find(Post.class,post.getId()).replies.size()).isEqualTo(1);
    }

    @Test
    @Rollback(value = false)
    void 댓글여러개(){
        Category category = em.createQuery("select c from Category c where c.name = :name ",Category.class)
                .setParameter("name","categoryC")
                .getSingleResult();

        User userA = em.createQuery("select u from User u where u.name = :name",User.class)
                .setParameter("name","userA")
                .getSingleResult();

        User userB = em.createQuery("select u from User u where u.name = :name",User.class)
                .setParameter("name","userB")
                .getSingleResult();

        Content content = new Content("내용 콘텐츠입니다.");
        em.persist(content);

        Post post = new Post(category, userA, "제목입니다", content);
        em.persist(post);

        //userA의 댓글
        Reply reply1 = new Reply(post, userA, "댓글입니다11");
        //userB의 댓글
        Reply reply2 = new Reply(post, userB, "댓글입니다22");
        em.persist(reply1);
        em.persist(reply2);

        em.flush();
        em.clear();

        assertThat(em.find(Post.class,post.getId()).replies.size()).isEqualTo(2);

    }


}