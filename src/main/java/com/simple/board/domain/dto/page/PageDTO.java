package com.simple.board.domain.dto.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageDTO {

    //페이징 범위 ex) 1~5
    public static final int PAGE_RANGE = 5;

    private int nowPage;
    private int startPage;
    private int endPage;
    private boolean hasPre;
    private boolean hasNext;

    public PageDTO(Page page) {
        nowPage = page.getNumber()+1;
        startPage = (nowPage-1) / PAGE_RANGE * PAGE_RANGE + 1;
        endPage = Math.min(startPage + PAGE_RANGE-1, page.getTotalPages());

        hasPre = page.hasPrevious();
        hasNext = page.hasNext();
    }

}
