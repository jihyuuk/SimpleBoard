package com.simple.board.service;

import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.Reply;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostService postService;
    private final UserService userService;

    @Transactional
    public void save(Long postId, String comment){
        Post post = postService.findById(postId);
        User user = userService.findByName("userA");
        Reply reply = new Reply(post,user,comment);
        replyRepository.save(reply);
    }
}
