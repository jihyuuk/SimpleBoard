package com.simple.board.controller;

import com.simple.board.domain.dto.Reply.ReplyDTO;
import com.simple.board.domain.dto.like.PostLikeDTO;
import com.simple.board.domain.dto.like.ReplyLikeDTO;
import com.simple.board.domain.dto.post.PostDTO;
import com.simple.board.domain.dto.post.PostFormDTO;
import com.simple.board.domain.entity.*;
import com.simple.board.service.CategoryService;
import com.simple.board.service.LikeService;
import com.simple.board.service.PostService;
import com.simple.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PostController {

    private final PostService postService;
    private final ReplyService replyService;
    private final CategoryService categoryService;
    private final LikeService likeService;

    //테스트용
    private final InItDatas inItDatas;

    @GetMapping("/post/{id}")
    @PreAuthorize("permitAll()")
    public String readPost(@PathVariable Long id, Model model,Authentication authentication){

        //post 존재 여부 체크
        PostExistCheck(id);

        //게시글 가져오기
        PostDTO postDTO = postService.getPostDTO(id);

        //댓글 가져오기
        List<ReplyDTO> replyDTOs = replyService.getReplyDTOs(id);

        //좋아요 정보 이거 쿼리좀 확인해야함
        PostLikeDTO postLikeDTO = getPostLikeDTO(id, authentication);
        ReplyLikeDTO replyLikeDTO = getReplyLikeDTO(id, authentication);

        model.addAttribute("post",postDTO);
        model.addAttribute("replies",replyDTOs);
        model.addAttribute("postLike",postLikeDTO);
        model.addAttribute("replyLikes",replyLikeDTO);
        return "/post/postView";
    }



    @GetMapping("/new")
    public String newForm(Model model){
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories",categories);
        return "/post/newForm";
    }

    @PostMapping("/new")
    public String createPost(PostFormDTO postFormDTO, Authentication authentication){
        Long postId = postService.save(postFormDTO, authentication.getName());
        return "redirect:/post/"+postId;
    }

    @GetMapping("/post/{id}/edit")
    public String editForm(@PathVariable Long id, Model model,Authentication authentication){

        //id랑 유저 이름으로 post 찾아오기
        PostFormDTO postFormDTO = postService.findPostFormDTO(id, authentication.getName());

        //유저가 작성한 게시물이 맞는지
        beforeEditCheck(postFormDTO);

        model.addAttribute("post",postFormDTO);
        return "/post/editForm";
    }


    @PostMapping("/post/{id}/edit")
    public String updatePost(PostFormDTO postFormDTO, Authentication authentication){
        postService.update(postFormDTO,authentication.getName());
        return "redirect:/post/"+postFormDTO.getId();
    }

    @GetMapping("/post/{id}/delete")
    public String deletePost(@PathVariable Long id,Authentication authentication){
        postService.delete(id,authentication.getName());
        return "redirect:/";
    }




    //post의 id가 테이블에 존재하는지 판별
    private void PostExistCheck(Long id) {
        //게시글 존재 x -> 404에러
        if(!postService.existById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    //작성자인지 확인하는 메서드
    private boolean isAuthor(Authentication authentication, String userName) {
        return authentication != null && userName.equals(authentication.getName());
    }

    //좋아요 DTO 생성 관련...
    private ReplyLikeDTO getReplyLikeDTO(Long id, Authentication authentication) {
        return (authentication == null) ? new ReplyLikeDTO() : likeService.getReplyLikeDTO(postService.getReferenceById(id), authentication.getName());
    }
    private PostLikeDTO getPostLikeDTO(Long id, Authentication authentication) {
        return (authentication == null) ? new PostLikeDTO() : likeService.getPostLikeDTO(id, authentication.getName());
    }

    private static void beforeEditCheck(PostFormDTO postFormDTO) {
        if(postFormDTO == null){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    //테스트 데이터
    @PostConstruct
    void init(){
        inItDatas.init();
    }

}