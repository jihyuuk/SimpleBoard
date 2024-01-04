package com.simple.board.controller;

import com.simple.board.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/post/{postId}/like")
    public String postLike(@PathVariable Long postId, Authentication authentication,boolean isLiked){
        String userName = authentication.getName();
        likeService.postLikeRequest(postId,userName,isLiked);
        return "redirect:/post/"+postId;
    }

    @GetMapping("/reply/{replyId}/like")
    public String replyLike(@PathVariable Long replyId, Authentication authentication,boolean isLiked,String redirect){
        String userName = authentication.getName();
        likeService.replyLikeRequest(replyId,userName,isLiked);
        return "redirect:"+redirect;
    }

}
