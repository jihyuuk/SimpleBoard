package com.simple.board.controller;

import com.simple.board.entity.Board;
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

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    //페이징 범위 ex) 1~5
    public static final int PAGE_RANGE = 5;
    private final BoardService boardService;

    @GetMapping
    public String boardList(Model model, @RequestParam(value = "page",defaultValue = "0") int nowPage, @PageableDefault(sort = "id",direction = Sort.Direction.DESC) Pageable pageable){

        //페이지 음수시 첫 페이지로 리다이렉트
        if(nowPage < 0){
            return "redirect:/board";
        }

        //페이지별로 정보 찾아오기
        Page<Board> boardList = boardService.findByPage(pageable);
        int totalPages = boardList.getTotalPages();

        //페이지 초과시 마지막 페이지로 리다이렉트
        if(nowPage >= totalPages){
            return "redirect:/board?page="+(totalPages-1);
        }

        //페이징
        int startPage = nowPage / PAGE_RANGE * PAGE_RANGE;
        int endPage = Math.min(startPage + PAGE_RANGE, totalPages)-1;
        boolean hasPre = startPage != 0;
        boolean hasNext = endPage != boardList.getTotalPages()-1;

        model.addAttribute("boardList", boardList);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("pageRange",PAGE_RANGE);
        model.addAttribute("hasPre",hasPre);
        model.addAttribute("hasNext",hasNext);

        return "/board/boardList";
    }

    @GetMapping("/read/{id}")
    public String read(@PathVariable int id,@RequestParam(defaultValue = "0") int page, Model model){

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
