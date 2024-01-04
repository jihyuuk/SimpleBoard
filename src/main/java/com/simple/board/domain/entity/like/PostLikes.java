package com.simple.board.domain.entity.like;

import com.simple.board.domain.entity.Post;
import com.simple.board.domain.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikes {

    @Id @GeneratedValue
    @Column(name = "post_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isLiked;

    public PostLikes(Post post, User user, boolean isLiked) {
        this.post = post;
        this.user = user;
        this.isLiked = isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
    }
}
