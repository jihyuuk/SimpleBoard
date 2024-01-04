package com.simple.board.domain.entity.like;

import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.Reply;
import com.simple.board.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReplyLikes {

    @Id
    @GeneratedValue
    @Column(name = "reply_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isLiked;

    public ReplyLikes(Reply reply, User user, boolean isLiked) {
        this.reply = reply;
        this.user = user;
        this.isLiked = isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }
}