package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.*;
import com.simple.board.service.CategoryService;
import com.simple.board.service.ContentService;
import com.simple.board.service.PostService;
import com.simple.board.service.UserService;
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

    //테스트용
    private final InItDatas inItDatas;

    @GetMapping("/post/{id}")
    public String readPost(@PathVariable Long id, Model model){
        PostDTO postDTO = postService.findDTOById(id);
        model.addAttribute("post",postDTO);
        return "/post/postView";
    }

    @GetMapping("/new")
    public String newForm(Model model){
        //지금은 객체를 넘기지만 나중에 dto 넘길지 고민좀
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "/post/newForm";
    }

    @Transactional
    @PostMapping("/new")
    public String createPost(Long categoryId, String title, String text){
        Long postId = postService.save(categoryId, title, text);
        return "redirect:/post/"+postId;
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model){
        PostDTO postDTO = postService.findDTOById(id);
        model.addAttribute("post",postDTO);
        return "/post/editForm";
    }

    @PostMapping("/post/{id}/edit")
    public String updatePost(@PathVariable Long id,String title,String text){
        postService.update(id,title,text);
        return "redirect:/post/"+id;
    }


    //테스트 데이터
    @PostConstruct
    void init(){
        inItDatas.init();
    }

}