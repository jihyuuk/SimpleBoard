package com.simple.board.domain.dto.like;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostLikeDTO {

    boolean isLiked;
    boolean isHated;

    public PostLikeDTO(boolean isLiked, boolean isHated) {
        this.isLiked = isLiked;
        this.isHated = isHated;
    }
}
