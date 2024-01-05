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
    private String lastModifiedDate;

    public ReplyDTO(Long id, String userName, String comment, int likes, int hates, LocalDateTime lastModifiedDate) {
        this.id = id;
        this.userName = userName;
        this.comment = comment;
        this.likes = likes;
        this.hates = hates;
        this.lastModifiedDate = TimeCalculator.calculate(lastModifiedDate);
    }
}
