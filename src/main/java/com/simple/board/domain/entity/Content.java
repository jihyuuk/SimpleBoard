package com.simple.board.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Id @GeneratedValue
    @Column(name = "content_id")
    private Long id;
    @Lob
    private String contents;

    public Content(String contents) {
        this.contents = contents;
    }

    //수정테스트
    public void setContents(String contents) {
        this.contents = contents;
    }
}
