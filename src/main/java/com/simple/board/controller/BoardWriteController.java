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
@RequestMapping("/write")
public class BoardWriteController {

    private final BoardService boardService;
    @GetMapping
    public String writeForm(Model model){
        model.addAttribute("board",new Board());
        return "/board/writeForm";
    }

    @PostMapping
    public String boardSave(Board board){
        boardService.save(board);
        return "redirect:/read/"+board.getId();
    }


}
