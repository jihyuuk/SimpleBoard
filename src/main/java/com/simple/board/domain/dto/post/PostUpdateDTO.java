package com.simple.board.domain.dto.post;

import com.simple.board.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDTO {

    private Long id;
    private String title;
    private String content;

    public PostUpdateDTO(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }

}
