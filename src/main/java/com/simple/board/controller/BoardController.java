package com.simple.board.controller;

import com.simple.board.domain.Post;
import com.simple.board.model.PageDTO;
import com.simple.board.model.PostNewDTO;
import com.simple.board.model.PostUpdateDTO;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    @GetMapping
    public String boardList(@PageableDefault(sort = "id",direction = DESC) Pageable pageable, Model model){

        //해당 페이지의 게시글들 가져오기
        Page<Post> page = boardService.findByPage(pageable);

        //나중에 검증으로 빼기
        //page=20000 처럼 content 없는 page 요청시 홈으로 redirect
        if (!page.hasContent()){
            return "redirect:/board?page="+page.getTotalPages();
        }

        //페이징 처리
        PageDTO pageDTO = new PageDTO(page);
        model.addAttribute("pageList",page.getContent());
        model.addAttribute("pageDTO",pageDTO);
        return "/board/boardList";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable Long id,@RequestParam(defaultValue = "1") int page, Model model){

        Post findBoard = boardService.findById(id);
        model.addAttribute("board",findBoard);
        model.addAttribute("page",page);

        return "/board/boardRead";
    }

    @GetMapping("/create")
    public String writeForm(Model model){
        model.addAttribute("board",new PostNewDTO());
        return "/board/boardCreate";
    }

    @PostMapping("/create")
    public String boardSave(PostNewDTO postNew){
        Long postId = boardService.save(postNew);
        return "redirect:/board/read/"+postId;
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model){
        Post board = boardService.findById(id);
        model.addAttribute("board",board);
        return "/board/boardEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateById(@PathVariable Long id, String title, String content) {

        boardService.update(new PostUpdateDTO(id,title,content));

        return "redirect:/board/read/"+id;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id){
        boardService.deleteById(id);
        return "redirect:/board";
    }



}
