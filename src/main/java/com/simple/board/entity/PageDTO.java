package com.simple.board.entity;

import lombok.Getter;

@Getter
public class PageDTO {

    //페이징 범위 ex) 1~5
    public static final int PAGE_RANGE = 5;

    private int nowPage;
    private int startPage;
    private int endPage;
    private boolean hasPre;
    private boolean hasNext;

    public PageDTO(int nowPage,int totalPages) {
        this.nowPage = nowPage;
        startPage = nowPage / PAGE_RANGE * PAGE_RANGE;
        endPage = Math.min(startPage + PAGE_RANGE, totalPages)-1;
        hasPre = startPage != 0;
        hasNext = endPage != totalPages-1;
    }
}
