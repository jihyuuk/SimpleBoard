package com.simple.board.domain.dto.post;

import com.simple.board.domain.entity.*;
import com.simple.board.domain.entity.like.PostLike;
import lombok.*;

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

    private int views;

    private String lastModifiedDate;

    public PostDTO(Post post) {
        id = post.getId();
        categoryName = post.getCategory().getName();
        userName = post.getUser().getName();
        title = post.getTitle();
        content = post.getContent().getText();
        replies =  post.getReplies().stream().filter(Reply::isEnabled).collect(Collectors.toList());
        views = post.getViews();
        lastModifiedDate = post.getLastModifiedDate();
    }
}
