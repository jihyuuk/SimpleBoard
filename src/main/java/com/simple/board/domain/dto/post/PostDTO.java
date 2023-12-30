package com.simple.board.domain.dto.post;

import com.simple.board.domain.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostDTO {

    private Long id;
    private String categoryName;
    private String userName;
    private String title;
    private String content;
    //ReplyDTO로 변경해야함
    private List<Reply> replies = new ArrayList<>();

    private int likes;
    private int hates;
    private int views;

    private LocalDateTime lastModifiedDate;

    public PostDTO(Post post) {
        id = post.getId();
        categoryName = post.getCategory().getName();
        userName = post.getUser().getName();
        title = post.getTitle();
        content = post.getContent().getText();
        replies =  post.getReplies().stream().filter(Reply::isEnabled).collect(Collectors.toList());
        likes = post.getLikes();
        hates = post.getHates();
        views = post.getViews();
        lastModifiedDate = post.getLastModifiedDate();
    }
}
