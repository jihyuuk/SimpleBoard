package com.simple.board.domain.dto.like;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReplyLikeDTO {

    Long Id;
    boolean isLiked;

}
