package com.simple.board.domain.dto.post;

import com.simple.board.domain.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostFormDTO {

    private Long id;
    private Long categoryId;
    private String categoryName;
    private String title;
    private String content;

    public PostFormDTO(Long id, Long categoryId, String categoryName, String title, String content) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.title = title;
        this.content = content;
    }

    public PostFormDTO(Long categoryId, String title, String content) {
        this.categoryId = categoryId;
        this.title = title;
        this.content = content;
    }
}
