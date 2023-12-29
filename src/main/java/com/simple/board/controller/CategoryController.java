package com.simple.board.controller;

import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.entity.Category;
import com.simple.board.service.CategoryService;
import com.simple.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

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
        //dto로 변환시키기
        List<PostDTO> postDTOList = category.getPosts().stream().map(PostDTO::new).collect(Collectors.toList());

        model.addAttribute("list", postDTOList);
        return "/board/boardList";
    }

}
