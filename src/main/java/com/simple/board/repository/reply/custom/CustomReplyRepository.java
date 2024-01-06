package com.simple.board.repository.reply.custom;

import com.simple.board.domain.dto.Reply.ReplyDTO;

import java.util.List;

public interface CustomReplyRepository {

    List<ReplyDTO> findALLReplyDTO(Long postId);

}
