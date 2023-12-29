package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.*;
import com.simple.board.service.CategoryService;
import com.simple.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;
    private final InItDatas inItDatas;
    private final EntityManager em;

    @GetMapping("/post/{id}")
    public String readPost(@PathVariable Long id, Model model){
        PostDTO postDTO = postService.findDTOById(id);
        model.addAttribute("post",postDTO);
        return "/post/postView";
    }

    @GetMapping("/new")
    public String newPostForm(Model model){
        //지금은 객체를 넘기지만 나중에 dto 넘길지 고민좀
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "/post/postForm";
    }

    @Transactional
    @PostMapping("/new")
    public String createPost(Long categoryId, String title, String text){
        log.info("category : {}",categoryId);
        log.info("title : {}",title);
        log.info("text : {}",text);

        Category findCategory = categoryService.findById(categoryId);
        //userService, contentService, postService 만들어야함
        User userA = (User) em.createQuery("select u from User u where u.name = :name")
                .setParameter("name", "userA")
                .getSingleResult();
        Content content = new Content(text);
        em.persist(content);
        Post post = new Post(findCategory, userA, title,content);
        em.persist(post);

        return "redirect:/post/"+post.getId();
    }


    //테스트 데이터
    @PostConstruct
    void init(){
        inItDatas.init();
    }

}