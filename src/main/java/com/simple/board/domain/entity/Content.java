package com.simple.board.domain.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Id @GeneratedValue
    @Column(name = "content_id")
    Long id;
    String text;

    public Content(String text) {
        this.text = text;
    }

    //수정테스트
    public void setText(String text) {
        this.text = text;
    }
}
