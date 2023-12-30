package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/")
    public String home(){
        //나중에 홈 꾸며야함
        return "home";
    }

    @GetMapping("/{categoryName}")
    public String category(@PathVariable String categoryName, Model model){
        
        //카테고리 찾아오기
        Category category = categoryService.findByName(categoryName);
        //카테고리 없으면 404
        if(category == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        List<PostDTO> postDTOList = category.getEnabledPostDTOS();

        model.addAttribute("list", postDTOList);
        return "/board/boardList";
    }

}
