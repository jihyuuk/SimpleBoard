//package com.simple.board.controller;
//
//import com.simple.board.domain.entity.Post;
//import com.simple.board.domain.dto.page.PageDTO;
//import com.simple.board.domain.dto.post.PostDTO;
//import com.simple.board.domain.dto.post.PostNewDTO;
//import com.simple.board.domain.dto.post.PostUpdateDTO;
//import com.simple.board.service.BoardService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//
//import static org.springframework.data.domain.Sort.Direction.DESC;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/board")
//public class BoardController {
//
//
//    private final BoardService boardService;
//
//    @GetMapping
//    public String boardList(@PageableDefault(sort = "id",direction = DESC) Pageable pageable, Model model){
//
//        //해당 페이지의 게시글들 가져오기
//        Page<PostDTO> page = boardService.findByPage(pageable);
//
//        //나중에 검증으로 빼기
//        //totalPage 보다 큰 page요청의 경우 리다이렉트
//        int totalPage = Math.max(page.getTotalPages(), 1);
//        int requesetPage = pageable.getPageNumber();
//        if (requesetPage > totalPage){
//            return "redirect:/board?page="+totalPage;
//        }
//
//        //페이징 처리
//        PageDTO pageDTO = new PageDTO(page);
//        model.addAttribute("pageList",page.getContent());
//        model.addAttribute("pageDTO",pageDTO);
//        return "board/boardHome";
//    }
//
//    @GetMapping("/{id}")
//    public String read(@PathVariable Long id,@RequestParam(defaultValue = "1") int page, Model model){
//
//        Post post = boardService.findById(id);
//        PostDTO postDTO = new PostDTO(post);
//
//        model.addAttribute("post",postDTO);
//        model.addAttribute("page",page);
//
//        return "article";
//    }
//
//    @GetMapping("/new")
//    public String writeForm(Model model){
//        model.addAttribute("postNewDTO",new PostNewDTO());
//        return "postForm";
//    }
//
//    @PostMapping("/new")
//    public String boardSave(PostNewDTO postNew){
//        Long postId = boardService.save(postNew);
//        return "redirect:/board/"+postId;
//    }
//
//    @GetMapping("/edit/{id}")
//    public String editForm(@PathVariable Long id, Model model){
//        Post findPost = boardService.findById(id);
//        PostUpdateDTO updateDTO = new PostUpdateDTO(findPost);
//
//        model.addAttribute("postUpdateDTO",updateDTO);
//        return "post/editPost";
//    }
//
//    @PostMapping("/edit/{id}")
//    public String updateById(PostUpdateDTO updateDTO) {
//
//        boardService.update(updateDTO);
//        return "redirect:/board/"+updateDTO.getId();
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteById(@PathVariable Long id){
//        boardService.deleteById(id);
//        return "redirect:/board";
//    }
//
//
//
//}
