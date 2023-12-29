package com.simple.board.domain.entity;

import com.simple.board.domain.auditing.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "post_id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    Category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    String title;

    @OneToOne
    @JoinColumn(name = "content_id")
    Content content;

    int likes;
    int hates;
    int views;
    boolean enabled;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<Reply> replies = new ArrayList<>();

    public Post(Category category, User user, String title, Content content) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.content = content;
        enabled = true;
    }

    //수정테스트
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
