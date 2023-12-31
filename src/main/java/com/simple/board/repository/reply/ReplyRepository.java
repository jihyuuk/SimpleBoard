package com.simple.board.repository.reply;

import com.simple.board.domain.entity.Reply;
import com.simple.board.repository.reply.custom.CustomReplyRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Long>, CustomReplyRepository {
}
