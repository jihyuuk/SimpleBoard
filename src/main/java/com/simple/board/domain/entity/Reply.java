package com.simple.board.domain.entity;

import com.simple.board.domain.auditing.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String comment;

    private int likes;
    private int hates;
    private boolean enabled;

    public Reply(Post post, User user, String comment) {
        this.post = post;
        this.user = user;
        this.comment = comment;
        enabled = true;

        post.getReplies().add(this);
    }

    public void updateComment(String comment){
        setLastModifiedDate(LocalDateTime.now());
        this.comment = comment;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setHates(int hates) {
        this.hates = hates;
    }
}
