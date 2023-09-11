package com.simple.board.controller;

import com.simple.board.entity.Board;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardReadController {

    private final BoardService boardService;

    @GetMapping("board/read/{id}")
    public String read(@PathVariable int id,@RequestParam(defaultValue = "0") int page, Model model){

        Board findBoard = boardService.findById(id);
        model.addAttribute("board",findBoard);
        model.addAttribute("page",page);

        return "/board/boardRead";
    }

}
