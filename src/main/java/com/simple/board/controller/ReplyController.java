package com.simple.board.controller;

import com.simple.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply/{postId}")
    public String addReply(@PathVariable Long postId, String comment, Authentication authentication){
        replyService.save(postId,comment,authentication.getName());
        return "redirect:/post/"+postId;
    }

    //수정 나중에 구현
    @PostMapping("/reply/{replyId}/edit")
    public String updateReply(Long replyId,String comment,Authentication authentication){
        Long postId = replyService.update(replyId, comment, authentication.getName());
        return "redirect:/post/"+postId;
    }

    @GetMapping("/reply/{replyId}/delete")
    public String removeReply(@PathVariable Long replyId,Authentication authentication){
        Long postId = replyService.delete(replyId,authentication.getName());
        return "redirect:/post/"+postId;
    }
}
