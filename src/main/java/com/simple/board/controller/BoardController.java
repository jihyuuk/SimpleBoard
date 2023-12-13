package com.simple.board.controller;

import com.simple.board.domain.Board;
import com.simple.board.model.PageDTO;
import com.simple.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {


    private final BoardService boardService;

    @GetMapping
    public String boardList(@PageableDefault(page = 1) Pageable pageable, Model model){

        //해당 페이지의 게시글들 가져오기
        Page<Board> boardList = boardService.findByPage(pageable);
        //페이징 처리
        PageDTO pageDTO = new PageDTO(pageable.getPageNumber(), boardList.getTotalPages());

        model.addAttribute("boardList",boardList);
        model.addAttribute("pageDTO",pageDTO);
        return "/board/boardList";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable int id,@RequestParam(defaultValue = "1") int page, Model model){

        Board findBoard = boardService.findById(id);
        model.addAttribute("board",findBoard);
        model.addAttribute("page",page);

        return "/board/boardRead";
    }

    @GetMapping("/create")
    public String writeForm(Model model){
        model.addAttribute("board",new Board());
        return "/board/boardCreate";
    }

    @PostMapping("/create")
    public String boardSave(Board board){
        boardService.save(board);
        return "redirect:/board/read/"+board.getId();
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Integer id, Model model){
        Board board = boardService.findById(id);
        model.addAttribute("board",board);
        return "/board/boardEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateById(@PathVariable Integer id, String title, String content) {
        Board board = boardService.findById(id);
        board.setTitle(title);
        board.setContent(content);
        boardService.save(board);

        return "redirect:/board/read/"+id;
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable Integer id){
        boardService.deleteById(id);
        return "redirect:/board";
    }



}
