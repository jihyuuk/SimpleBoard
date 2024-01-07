package com.simple.board.service;

import com.simple.board.domain.dto.Reply.ReplyDTO;
import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.Reply;
import com.simple.board.domain.entity.User;
import com.simple.board.repository.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostService postService;
    private final UserService userService;

    public Reply findById(Long id){
        return replyRepository.findById(id).get();
    }

    public List<ReplyDTO> getReplyDTOs(Long postId){
        return replyRepository.findALLReplyDTO(postId);
    }

    @Transactional
    public void save(Long postId, String comment, String userName){
        Post post = postService.findById(postId);
        User user = userService.findByName(userName);
        //post,user null체크 해야함

        Reply reply = new Reply(post,user,comment);
        replyRepository.save(reply);
    }

    @Transactional
    public Long update(Long replyId,String comment, String accessUser) {
        Reply reply = replyRepository.findById(replyId).orElse(null);
        beforeUpdateCheck(accessUser, reply);
        reply.updateComment(comment);

        return reply.getPost().getId();
    }

    @Transactional
    public Long delete(Long replyId, String accessUser) {
        //논리적 삭제 enabled = false로
        Reply reply = replyRepository.findById(replyId).orElse(null);
        beforeUpdateCheck(accessUser, reply);
        reply.setEnabled(false);

        return reply.getPost().getId();
    }

    private void beforeUpdateCheck(String accessUser, Reply reply) {
        if(reply == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(!reply.getUser().getName().equals(accessUser)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


}
