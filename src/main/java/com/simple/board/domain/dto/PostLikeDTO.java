package com.simple.board.domain.dto;

import lombok.Getter;

@Getter
public class PostLikeDTO {

    private Long likes;
    private Long hates;
    private Boolean Liked;

    public PostLikeDTO(Long likes, Long hates, Boolean liked) {
        this.likes = likes;
        this.hates = hates;
        Liked = liked;
    }
}
