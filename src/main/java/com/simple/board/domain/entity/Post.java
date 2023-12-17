package com.simple.board.domain.entity;

import com.simple.board.domain.dto.post.PostNewDTO;
import com.simple.board.domain.dto.post.PostUpdateDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    public Post(PostNewDTO postNew){
        title = postNew.getTitle();
        content = postNew.getContent();
    }

    public void update(PostUpdateDTO updateDTO) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
    }

}
