package com.simple.board.domain.dto.post;

import com.simple.board.domain.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostDTO {

    Long id;
    String categoryName;
    String userName;
    String title;
    String content;
    //ReplyDTO로 변경해야함
    List<Reply> replies = new ArrayList<>();

    int likes;
    int hates;
    int views;

    LocalDateTime lastModifiedDate;

    public PostDTO(Post post) {
        id = post.getId();
        categoryName = post.getCategory().getName();
        userName = post.getUser().getName();
        title = post.getTitle();
        content = post.getContent().getText();
        replies =  post.getReplies();
        likes = post.getLikes();
        hates = post.getHates();
        views = post.getViews();
        lastModifiedDate = post.getLastModifiedDate();
    }
}
