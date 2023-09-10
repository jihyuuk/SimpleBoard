package com.simple.board.controller;

import com.simple.board.entity.Board;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BoardReadController {

    private final BoardService boardService;

    @GetMapping("/read/{id}")
    public String read(@PathVariable int id, Model model){

        Board findBoard = boardService.findById(id);
        model.addAttribute("board",findBoard);

        return "/board/boardRead";
    }

}
