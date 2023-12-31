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
public class PostLike {

    @Id @GeneratedValue
    @Column(name = "post_like_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean liked;

    public PostLike(Post post, User user, boolean liked) {
        this.post = post;
        this.user = user;
        this.liked = liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
