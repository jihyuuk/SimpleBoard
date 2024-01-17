package com.simple.board.domain.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage {

    @Id @GeneratedValue
    @Column(name = "post_image_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    private String url;

    public PostImage(Content content, String url) {
        this.content = content;
        this.url = url;
    }
}
