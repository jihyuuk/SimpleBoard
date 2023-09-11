package com.simple.board.controller;

import com.simple.board.entity.Board;
import com.simple.board.repository.BoardRepository;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardEditController {

    private final BoardService boardService;

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board",board);
        return "/board/boardEdit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Integer id, String title, String content) {
        Board board = boardService.findById(id);
        board.setTitle(title);
        board.setContent(content);
        boardService.save(board);

        return "redirect:/board/read/"+id;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        boardService.deleteById(id);
        return "redirect:/board";
    }

}
