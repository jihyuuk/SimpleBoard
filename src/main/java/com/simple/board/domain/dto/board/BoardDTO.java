package com.simple.board.domain.dto.board;

import com.simple.board.domain.auditing.TimeCalculator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardDTO {

    private Long id;
    private String title;
    private String userName;
    private String createdDate;
    private int likes;
    private Long replys;
    private int views;

    public BoardDTO(Long id, String title, String userName, LocalDateTime createdDate, int likes, Long replys, int views) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.createdDate = TimeCalculator.calculate(createdDate);
        this.likes = likes;
        this.replys = replys;
        this.views = views;
    }
}
