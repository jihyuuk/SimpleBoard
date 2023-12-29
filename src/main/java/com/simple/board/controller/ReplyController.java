package com.simple.board.controller;

import com.simple.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/reply/{postId}")
    public String addReply(String comment, @PathVariable Long postId){
        replyService.save(postId,comment);
        return "redirect:/post/"+postId;
    }

    @DeleteMapping("/reply/{replyId}")
    public String removeReply(){
        return null;
    }
}
