package com.simple.board.domain.dto.post;

import com.simple.board.domain.auditing.TimeCalculator;
import com.simple.board.domain.entity.Post;
import lombok.Getter;

@Getter
public class PostDTO {

    private Long id;
    private String categoryName;
    private String userName;
    private String title;
    private String content;
    private int likes;
    private int hates;
    private int views;
    private boolean isModified;
    private String createdDate;

//    public PostDTO(Post post, String categoryName, String userName, String content) {
//        this.id = post.getId();
//        this.categoryName = categoryName;
//        this.userName = userName;
//        this.title = post.getTitle();
//        this.content = content;
//        this.likes = post.getLikes();
//        this.hates = post.getHates();
//        this.views = post.getViews();
//        this.lastModifiedDate = TimeCalculator.calculate(post.getLastModifiedDate());
//    }

    public PostDTO(Post post) {
        this.id = post.getId();
        this.categoryName = post.getCategory().getName();
        this.userName = post.getUser().getName();
        this.title = post.getTitle();
        this.content = post.getContent().getText();
        this.likes = post.getLikes();
        this.hates = post.getHates();
        this.views = post.getViews();
        this.isModified = post.isModified();
        this.createdDate = TimeCalculator.calculate(post.getCreatedDate());
    }
}
