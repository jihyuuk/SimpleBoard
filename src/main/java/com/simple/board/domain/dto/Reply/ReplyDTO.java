package com.simple.board.domain.dto.Reply;

import com.simple.board.domain.auditing.TimeCalculator;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ReplyDTO {

    private Long id;
    private String userName;
    private String comment;
    private int likes;
    private int hates;
    private String createdDate;

    public ReplyDTO(Long id, String userName, String comment, int likes, int hates, LocalDateTime createdDate) {
        this.id = id;
        this.userName = userName;
        this.comment = comment;
        this.likes = likes;
        this.hates = hates;
        this.createdDate = TimeCalculator.calculate(createdDate);
    }
}
