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
    public String like(@PathVariable Long postId, Authentication authentication,boolean liked){
        String userName = authentication.getName();
        likeService.likeRequest(postId,userName,liked);
        return "redirect:/post/"+postId;
    }

}
