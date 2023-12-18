package com.simple.board.domain.dto.page;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageDTO {

    //페이징 범위 ex) 1~5
    public static final int PAGE_RANGE = 5;

    private int totalPage;
    private int nowPage;
    private int startPage;
    private int endPage;
    private boolean hasPre;
    private boolean hasNext;

    public PageDTO(Page page) {
        totalPage = Math.max(page.getTotalPages(),1);
        nowPage = page.getNumber()+1;
        startPage = (nowPage-1) / PAGE_RANGE * PAGE_RANGE + 1;
        endPage = Math.min(startPage + PAGE_RANGE-1, totalPage);

        hasPre = page.hasPrevious();
        hasNext = page.hasNext();
    }

}
