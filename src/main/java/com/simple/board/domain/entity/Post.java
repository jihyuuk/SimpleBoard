package com.simple.board.domain.entity;

import com.simple.board.domain.auditing.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private int likes;
    private int hates;
    private int views;
    private boolean isModified;
    private boolean enabled;

    @OneToMany(mappedBy = "post")
    private List<Reply> replies = new ArrayList<>();

    public Post(Category category, User user, String title, Content content) {
        this.category = category;
        this.user = user;
        this.title = title;
        this.content = content;
        enabled = true;
    }

    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }
    public void setLikes(int likes) {
        this.likes = likes;
    }
    public void setHates(int hates) {
        this.hates = hates;
    }

    public void update(String title, String content) {
        setLastModifiedDate(LocalDateTime.now());
        this.title = title;
        this.content.setText(content);
        this.isModified = true;
    }

    public void viewUp(){
        this.views++;
    }
}
