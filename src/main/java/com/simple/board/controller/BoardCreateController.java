package com.simple.board.controller;

import com.simple.board.entity.Board;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/create")
public class BoardCreateController {

    private final BoardService boardService;
    @GetMapping
    public String writeForm(Model model){
        model.addAttribute("board",new Board());
        return "/board/boardCreate";
    }

    @PostMapping
    public String boardSave(Board board){
        boardService.save(board);
        return "redirect:/board/read/"+board.getId();
    }


}
