package com.simple.board.domain.dto;

import lombok.Getter;

@Getter
public class PostLikeDTO {

    boolean isLiked;
    boolean isHated;

    public PostLikeDTO(boolean isLiked, boolean isHated) {
        this.isLiked = isLiked;
        this.isHated = isHated;
    }
}
