package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.*;
import com.simple.board.service.CategoryService;
import com.simple.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    //테스트용
    private final InItDatas inItDatas;

    @GetMapping("/post/{id}")
    @PreAuthorize("permitAll()")
    public String readPost(@PathVariable Long id, Model model, Authentication authentication){
        Post post = postService.findById(id);

        //존재하지 않는 post 요청시
        checkNullPost(post);

        model.addAttribute("post",new PostDTO(post));
        model.addAttribute("isAuthor",isAuthor(authentication,post));
        return "/post/postView";
    }



    @GetMapping("/new")
    public String newForm(Model model){
        //지금은 객체를 넘기지만 나중에 dto 넘길지 고민좀
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "/post/newForm";
    }

    @PostMapping("/new")
    public String createPost(Long categoryId, String title, String text, Authentication authentication){
        Long postId = postService.save(categoryId, title, text, authentication.getName());
        return "redirect:/post/"+postId;
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,Authentication authentication){
        Post post = postService.findById(id);

        //존재하지 않는 post 요청시
        checkNullPost(post);

        //작성자아니면 403에러
        if(!isAuthor(authentication, post)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        model.addAttribute("post",new PostDTO(post));
        return "/post/editForm";
    }


    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable Long id,String title,String text, Authentication authentication){
        postService.update(id,title,text,authentication.getName());
        return "redirect:/post/"+id;
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id,Authentication authentication){
        postService.delete(id,authentication.getName());
        return "redirect:/";
    }

    private void checkNullPost(Post post) {
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //작성자인지 확인하는 메서드
    private boolean isAuthor(Authentication authentication, Post post) {
        return authentication != null && post.getUser().getName().equals(authentication.getName());
    }

    //테스트 데이터
    @PostConstruct
    void init(){
        inItDatas.init();
    }

}