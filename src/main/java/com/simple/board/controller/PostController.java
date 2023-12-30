package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.*;
import com.simple.board.service.CategoryService;
import com.simple.board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CategoryService categoryService;

    //테스트용
    private final InItDatas inItDatas;

    @GetMapping("/post/{id}")
    public String readPost(@PathVariable Long id, Model model, Authentication authentication){
        PostDTO postDTO = postService.findDTOById(id);

        //본인이 작성자인지 여부
        boolean isAuthor = isAuthor(authentication,postDTO);

        model.addAttribute("post",postDTO);
        model.addAttribute("isAuthor",isAuthor);
        return "/post/postView";
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newForm(Model model){
        //지금은 객체를 넘기지만 나중에 dto 넘길지 고민좀
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "/post/newForm";
    }

    @PostMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String createPost(Long categoryId, String title, String text, Authentication authentication){
        Long postId = postService.save(categoryId, title, text, authentication.getName());
        return "redirect:/post/"+postId;
    }

    @GetMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String editForm(@PathVariable Long id, Model model,Authentication authentication){
        PostDTO postDTO = postService.findDTOById(id);
        
        //작성자아니면 리다이렉트
        if(!isAuthor(authentication, postDTO)){
            return "redirect:/post/"+id;
        }
        model.addAttribute("post",postDTO);
        return "/post/editForm";
    }


    @PostMapping("/post/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String updatePost(@PathVariable Long id,String title,String text, Authentication authentication){
        postService.update(id,title,text,authentication.getName());
        return "redirect:/post/"+id;
    }

    @GetMapping("/post/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deletePost(@PathVariable Long id,Authentication authentication){
        postService.delete(id,authentication.getName());
        return "redirect:/";
    }


    //작성자인지 확인하는 메서드
    private boolean isAuthor(Authentication authentication, PostDTO postDTO) {
        return authentication != null && postDTO.getUserName().equals(authentication.getName());
    }

    //테스트 데이터
    @PostConstruct
    void init(){
        inItDatas.init();
    }

}