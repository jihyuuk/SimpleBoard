package com.simple.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class PostUpdateDTO {

    private Long id;
    private String title;
    private String content;

}
