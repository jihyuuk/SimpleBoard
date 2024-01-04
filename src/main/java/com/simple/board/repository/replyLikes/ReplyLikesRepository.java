package com.simple.board.repository.replyLikes;

import com.simple.board.domain.dto.like.ReplyLikeDTO;
import com.simple.board.domain.entity.Reply;
import com.simple.board.domain.entity.like.ReplyLikes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyLikesRepository extends JpaRepository<ReplyLikes,Long> {

    ReplyLikes findByReplyIdAndUser_name(Long id, String name);

    //List<ReplyLikes> findAllByPostIdAndUser_name(Long id, String name);

    List<ReplyLikes> findAllByReplyInAndUser_nameAndIsLiked(List<Reply> replies, String name, boolean isLiked);

}
